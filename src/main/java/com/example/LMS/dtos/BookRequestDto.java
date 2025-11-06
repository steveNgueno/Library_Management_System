package com.example.LMS.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookRequestDto(
        @NotBlank(message= "book's title is required")String title,
        @NotBlank(message= "book's author is required")String author,
        LocalDate publicationYear,
        Long genderId,
        @Min(value = 1, message = "number of copies must be at least 1") @NotNull(message= "number of copies of the book is required")
        int numOfCopies) {
}
