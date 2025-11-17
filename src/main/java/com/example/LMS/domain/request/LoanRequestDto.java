package com.example.LMS.domain.request;

import jakarta.validation.constraints.NotBlank;

public record LoanRequestDto(
        @NotBlank(message= "student's email is required")String studentEmail,
        @NotBlank(message= "book's title is required")String bookTitle) {
}
