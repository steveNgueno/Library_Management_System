package com.example.LMS.dtos;

import java.time.LocalDate;

public record BookRequestDto(String title, String author, LocalDate publicationYear, Long genderId, int numOfCopies) {
}
