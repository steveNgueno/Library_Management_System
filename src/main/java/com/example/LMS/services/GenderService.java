package com.example.LMS.services;


import com.example.LMS.dtos.GenderRequestDto;
import com.example.LMS.dtos.GenderResponseDto;

import java.util.List;

public interface GenderService {

    GenderResponseDto save(GenderRequestDto dto);

    GenderResponseDto getById(Long id);

    void deleteById(Long id);

    GenderResponseDto updateById(Long id, GenderResponseDto genderResponseDto);

    List<GenderResponseDto> getAll();
}
