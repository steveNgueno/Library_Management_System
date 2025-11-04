package com.example.LMS.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record StudentRequestDto(
        @NotBlank(message = "firstname is required") String firstname,
        @NotBlank(message = "lastname is required") String lastname,
        @NotBlank(message = "birthday is required") LocalDate birthday,
        @NotBlank(message = "email is required") @Email(message="\"email\": \"must be a well-formed email address\"\n") String email,
        @NotBlank(message= "Phone number is required") String phone) {
}
