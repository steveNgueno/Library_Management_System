package com.example.LMS.domain.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudentRequestDto(
        @NotBlank(message = "firstname is required") String firstname,
        String lastname,
        @NotNull(message = "birthday is required") @Past(message= "birthday must be in the past")LocalDate birthday,
        @NotBlank(message = "email is required") @Email(message="must be a well-formed email address") String email,
        @NotBlank(message= "Phone number is required") String phone,
        @NotBlank(message ="password is required") @Size(min= 4, max= 8) String password,
        @NotBlank(message= "Program is required") String program,
        @NotBlank(message="Student ID number is required") String studentId)
{
}
