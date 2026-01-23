package com.example.LMS.service.impl;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Status;
import com.example.LMS.domain.request.BookRequestDto;
import com.example.LMS.domain.response.BookResponseDto;
import com.example.LMS.exception.BookNotFoundException;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.mapper.BookMapper;
import com.example.LMS.domain.model.Book;
import com.example.LMS.domain.model.Gender;
import com.example.LMS.repository.BookRepository;
import com.example.LMS.repository.GenderRepository;
import com.example.LMS.service.BookService;
import com.example.LMS.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenderRepository genderRepository;
    private final HistoryService historyService;

    @Override
    public BookResponseDto saveBook(BookRequestDto request) {

         //check if the gender exists
        Gender gender = findGenderById(request.genderId());

        if(bookRepository.existsByTitle(request.title())){
            historyService.create(Status.FAILED,Action.ADD_BOOK, "failed to add book");
            throw new BusinessLogicException("Book with title '"+request.title()+"' already exists", "BOOK_ALREADY_EXISTS");
        }

        Book book = bookMapper.toEntity(request);
        book.setAvailableCopies(request.numOfCopies());
        book.setGender(gender);


        //save the book in the database
        Book savedBook = bookRepository.save(book);
        historyService.create(Status.SUCCESS, Action.ADD_BOOK,String.format("The book %s has been added",request.title()));

        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookResponseDto getBookById(Long id) {

        Book book = findBookById(id);

        return bookMapper.toDto(book);
    }


    @Override
    public BookResponseDto updateBookById(Long id, BookRequestDto request) {

        //check if the book exists
        Book book = findBookById(id);

        int borrowedBook = book.getNumOfCopies() - book.getAvailableCopies();
        int numOfCopies = book.getNumOfCopies();
        int NewNumOfCopies = request.numOfCopies();
        int availableCopies = book.getAvailableCopies();
        int difference;


        if(NewNumOfCopies < borrowedBook){
            historyService.create(Status.FAILED, Action.UPDATE_BOOK,String.format("Failed to update '%s' ",request.title()));
            throw new BusinessLogicException("The number of copies can't be less than the number of copies currently on loan", "BAD_REQUEST");
        }

        if(NewNumOfCopies > numOfCopies){
            difference = NewNumOfCopies - numOfCopies;
            book.setAvailableCopies(availableCopies + difference);
        }else{
            difference = numOfCopies - NewNumOfCopies;
            book.setAvailableCopies(availableCopies - difference);
        }

        book.setNumOfCopies(request.numOfCopies());

        book.setGender(request.genderId() == null ? book.getGender() : findGenderById(request.genderId()));

        Book savedBook = bookRepository.save(book);
        historyService.create(Status.SUCCESS, Action.UPDATE_BOOK,String.format("The book %s has been updated",request.title()));
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {

        List<Book> books = bookRepository.findAll();

        return bookMapper.toDtoList(books);
    }

    @Override
    public void deleteBookById(Long id) {

        //check if the book exists
        Book book = findBookById(id);

        if(book.getAvailableCopies() != book.getNumOfCopies()){
            historyService.create(Status.FAILED, Action.REMOVE_BOOK,String.format("Failed to remove '%s' ",book.getTitle()));
            throw new BusinessLogicException("This book can't be removed because there is one or more copies that have been borrowed","NOT-REMOVABLE_BOOK");
        }

        bookRepository.deleteById(id);
        historyService.create(Status.SUCCESS, Action.REMOVE_BOOK,String.format("The book %s has been updated",book.getTitle()));

    }

    private Book findBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    private Gender findGenderById(Long id){
        return genderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("gender not found"));
    }

}
