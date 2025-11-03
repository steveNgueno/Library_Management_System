package com.example.LMS.services.impl;

import com.example.LMS.dtos.BookRequestDto;
import com.example.LMS.dtos.BookResponseDto;
import com.example.LMS.mappers.BookMapper;
import com.example.LMS.models.Book;
import com.example.LMS.models.Gender;
import com.example.LMS.repositories.BookRepository;
import com.example.LMS.repositories.GenderRepository;
import com.example.LMS.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenderRepository genderRepository;

    @Override
    public BookResponseDto saveBook(BookRequestDto request) {

         //check if the gender exists
        Gender gender = genderRepository.findById(request.genderId()).orElseThrow(() -> new RuntimeException("gender not found"));

        Book book = bookMapper.toEntity(request);
        book.setGender(gender);

        //save the book in the database
        Book savedBook = bookRepository.save(book);

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

        book.setNumOfCopies(request.numOfCopies());

        Book savedBook = bookRepository.save(book);

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
        findBookById(id);

        bookRepository.deleteById(id);

    }

    private Book findBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException(format("Book with this id: %s not found", id)));
    }

}
