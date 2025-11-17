package com.example.LMS.service;

import com.example.LMS.domain.request.BookRequestDto;
import com.example.LMS.domain.response.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto saveBook(BookRequestDto request);

    BookResponseDto getBookById(Long id);

    void deleteBookById(Long id);

    BookResponseDto updateBookById(Long id, BookRequestDto request);

    List<BookResponseDto> getAllBooks();
}
