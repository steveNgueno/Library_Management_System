package com.example.LMS.dtos;

import java.time.LocalDate;

public record LoanResponseDto(LocalDate LoanDate,LocalDate returnDate,BookResponseDto book,StudentResponseDto student) {
}
