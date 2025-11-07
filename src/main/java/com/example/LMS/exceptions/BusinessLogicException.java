package com.example.LMS.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessLogicException extends RuntimeException {

    private String errorCode;

    public BusinessLogicException(String message, String errorCode) {
       super(message);
       this.errorCode = errorCode;
    }
}
