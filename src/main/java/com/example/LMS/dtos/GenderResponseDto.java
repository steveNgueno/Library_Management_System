package com.example.LMS.dtos;

import java.util.List;

public record GenderResponseDto(String name, List<BookResponseDto> books) {
}
