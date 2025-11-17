package com.example.LMS.domain.response;


import java.time.LocalDate;

public record AdminResponseDto(String firstname, String lastname, LocalDate birthday, String email, String phone,String program,String studentID,String staffId,String position) {
}
