package com.example.LMS.exception;

import static java.lang.String.format;

public class UserNotFoundException extends BusinessLogicException {

    public UserNotFoundException(Long id) {
        super(format("User with this id: %s not found", id), "USER_NOT_FOUND");
    }

    public UserNotFoundException(String email){
        super(format("User with this email: %s not found",email), "USER_NOT_FOUND");
    }
}
