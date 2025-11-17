package com.example.LMS.service;


import com.example.LMS.domain.request.StudentRequestDto;
import com.example.LMS.domain.response.StudentResponseDto;

import java.util.List;


public interface StudentService {

    StudentResponseDto save(StudentRequestDto request);

    StudentResponseDto getById(Long id);

    List<StudentResponseDto> getAll();

    StudentResponseDto update(Long id, StudentRequestDto request);

    void delete(Long id);
}
