package com.example.LMS.common;

public record ErrorDetails(int status, String errorCode, String message, String path, long timestamp) {

    public static ErrorDetails of(int status, String errorCode, String message, String path ){
        return new ErrorDetails(status, errorCode, message, path, System.currentTimeMillis());
    }
}
