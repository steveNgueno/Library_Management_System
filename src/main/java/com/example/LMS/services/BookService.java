package com.example.LMS.services;

import com.example.LMS.dtos.BookDto;
import com.example.LMS.models.Book;

import java.util.List;

public interface BookService {

    BookDto saveBook(BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    BookDto updateBookById(Long id, BookDto bookDto);

    List<BookDto> getAllBooks();
}
