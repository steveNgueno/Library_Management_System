package com.example.LMS.domain.response;

import java.time.LocalDate;
import java.util.List;


public record BookResponseDto(String title, String author, LocalDate publicationYear, String genderName, int numOfCopies, List<LoanResponseDto> loans) {
}
