package com.example.LMS.dtos;


import java.time.LocalDate;
import java.util.List;

public record StudentDto(String firstname, String lastname, LocalDate birthday, String email, List<LoanDto> loans) {
}
