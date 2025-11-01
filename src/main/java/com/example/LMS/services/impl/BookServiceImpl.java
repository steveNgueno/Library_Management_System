package com.example.LMS.services.impl;

import com.example.LMS.mappers.BookMapper;
import com.example.LMS.dtos.BookDto;
import com.example.LMS.models.Book;
import com.example.LMS.models.Gender;
import com.example.LMS.repositories.BookRepository;
import com.example.LMS.repositories.GenderRepository;
import com.example.LMS.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenderRepository genderRepository;

    @Override
    public BookDto saveBook(BookDto bookDto) {

         //check if the gender exists
        Gender gender = genderRepository.findById(bookDto.genderId()).orElseThrow(() -> new RuntimeException("gender not found"));

        Book book = bookMapper.toEntity(bookDto);
        book.setGender(gender);
        book.setLoans(new ArrayList<>());

        //save the book in the database
        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto getBookById(Long id) {

        Book book = findBookById(id);

        return bookMapper.toDto(book);
    }


    @Override
    public BookDto updateBookById(Long id, BookDto bookDto) {

        //check if the book exists
        Book book = findBookById(id);

        book.setNumOfCopies(bookDto.numOfCopies());

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> getAllBooks() {

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
