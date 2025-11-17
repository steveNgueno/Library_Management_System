package com.example.LMS.domain.request;

import jakarta.validation.constraints.NotBlank;

public record GenderRequestDto(@NotBlank(message= "gender's name is required")String name) {
}
