package com.example.LMS.domain.response;


import java.time.LocalDate;
import java.util.List;

public record StudentResponseDto(String firstname, String lastname, LocalDate birthday, String email, String phone, List<LoanResponseDto> loans) {
}
