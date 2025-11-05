package com.example.LMS.dtos;

import java.time.LocalDate;

public record LoanResponseDto(LocalDate loanDate,LocalDate returnDate,boolean isActive,String bookTitle,String emailStudent) {
}
