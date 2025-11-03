package com.example.LMS.services;

import com.example.LMS.dtos.BookRequestDto;
import com.example.LMS.dtos.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto saveBook(BookRequestDto request);

    BookResponseDto getBookById(Long id);

    void deleteBookById(Long id);

    BookResponseDto updateBookById(Long id, BookRequestDto request);

    List<BookResponseDto> getAllBooks();
}
