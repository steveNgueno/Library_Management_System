package com.example.LMS.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record StudentRequestDto(
        @NotBlank(message = "firstname is required") String firstname,
        String lastname,
        @NotNull(message = "birthday is required") @Past(message= "birthday must be in the past")LocalDate birthday,
        @NotBlank(message = "email is required") @Email(message="must be a well-formed email address") String email,
        @NotBlank(message= "Phone number is required") String phone) {
}
