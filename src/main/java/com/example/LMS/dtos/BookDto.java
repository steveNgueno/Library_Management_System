package com.example.LMS.dtos;

import java.time.LocalDate;
import java.util.List;


public record BookDto(String title, String author, LocalDate publicationYear, Long genderId, int numOfCopies, List<LoanDto> loans) {
}
