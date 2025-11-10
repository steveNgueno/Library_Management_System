package com.example.LMS.dtos;

import java.time.LocalDate;

public record LoanResponseDto(LocalDate loanDate, LocalDate returnDate,LocalDate expectedReturnDate, boolean active, String bookTitle, String emailStudent) {
}
