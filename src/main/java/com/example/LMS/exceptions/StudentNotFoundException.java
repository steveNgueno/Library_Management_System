package com.example.LMS.exceptions;

import static java.lang.String.format;

public class StudentNotFoundException extends BusinessLogicException {

    public StudentNotFoundException(Long id) {
        super(format("Student with this id: %s not found", id), "STUDENT_NOT_FOUND");
    }

    public StudentNotFoundException(String email){
        super(format("Student with this email: %s not found",email), "STUDENT_NOT_FOUND");
    }
}
