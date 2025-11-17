package com.example.LMS.exception;

import static java.lang.String.format;

public class BookNotFoundException extends BusinessLogicException {

    public BookNotFoundException(Long id) {
        super(format("Book with id: %s not found", id), "BOOK_NOT_FOUND");
    }

    public BookNotFoundException(String title){
        super(format("Book with this title: %s not found",title), "BOOK_NOT_FOUND");
    }
}
