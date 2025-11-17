package com.example.LMS.service;


import com.example.LMS.domain.request.GenderRequestDto;
import com.example.LMS.domain.response.GenderResponseDto;

import java.util.List;

public interface GenderService {

    GenderResponseDto save(GenderRequestDto dto);

    GenderResponseDto getById(Long id);

    void deleteById(Long id);

    GenderResponseDto updateById(Long id, GenderResponseDto genderResponseDto);

    List<GenderResponseDto> getAll();
}
