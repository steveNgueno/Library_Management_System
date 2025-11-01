package com.example.LMS.dtos;

public record ErrorDetails(int status, String error, String message, String path, long timestamp) {

    public static ErrorDetails of(int status, String error, String message, String path ){
        return new ErrorDetails(status, error, message, path, System.currentTimeMillis());
    }
}
