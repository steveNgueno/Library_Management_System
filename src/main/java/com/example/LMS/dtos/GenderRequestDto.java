package com.example.LMS.dtos;

import jakarta.validation.constraints.NotBlank;

public record GenderRequestDto(@NotBlank(message= "gender's name is required")String name) {
}
