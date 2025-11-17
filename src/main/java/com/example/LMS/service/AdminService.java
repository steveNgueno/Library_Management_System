package com.example.LMS.service;

import com.example.LMS.domain.request.AdminRequestDto;
import com.example.LMS.domain.response.AdminResponseDto;

import java.util.List;

public interface AdminService {

    AdminResponseDto save(AdminRequestDto request);

    AdminResponseDto getById(Long id);

    List<AdminResponseDto> getAll();

    AdminResponseDto update(Long id, AdminRequestDto request);

    void delete(Long id);
}
