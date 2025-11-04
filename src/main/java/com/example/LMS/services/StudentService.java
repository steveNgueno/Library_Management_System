package com.example.LMS.services;


import com.example.LMS.dtos.StudentRequestDto;
import com.example.LMS.dtos.StudentResponseDto;

import java.util.List;


public interface StudentService {

    StudentResponseDto save(StudentRequestDto request);

    StudentResponseDto getById(Long id);

    List<StudentResponseDto> getAll();

    StudentResponseDto update(Long id, StudentRequestDto request);

    void delete(Long id);
}
