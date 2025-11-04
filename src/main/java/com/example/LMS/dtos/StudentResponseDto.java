package com.example.LMS.dtos;


import java.time.LocalDate;
import java.util.List;

public record StudentResponseDto(String firstname, String lastname, LocalDate birthday, String email, String phone, List<LoanResponseDto> loans) {
}
