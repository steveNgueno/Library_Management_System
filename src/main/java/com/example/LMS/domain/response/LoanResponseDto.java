package com.example.LMS.domain.response;

import java.time.LocalDate;

public record LoanResponseDto(LocalDate loanDate, LocalDate returnDate,LocalDate expectedReturnDate, boolean active, String bookTitle, String emailStudent) {
}
