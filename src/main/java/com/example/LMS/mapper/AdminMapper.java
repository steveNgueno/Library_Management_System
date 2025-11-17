package com.example.LMS.mapper;

import com.example.LMS.domain.model.Admin;
import com.example.LMS.domain.request.AdminRequestDto;
import com.example.LMS.domain.response.AdminResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin toEntity(AdminRequestDto dto);

    AdminResponseDto toDto(Admin admin);

    List<AdminResponseDto> toDtoList(List<Admin> admins);
}
