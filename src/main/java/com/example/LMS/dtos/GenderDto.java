package com.example.LMS.dtos;

import java.util.List;

public record GenderDto(String name, List<BookDto> books) {
}
