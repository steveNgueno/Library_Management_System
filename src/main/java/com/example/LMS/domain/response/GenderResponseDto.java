package com.example.LMS.domain.response;

import java.util.List;

public record GenderResponseDto(String name, List<BookResponseDto> books) {
}
