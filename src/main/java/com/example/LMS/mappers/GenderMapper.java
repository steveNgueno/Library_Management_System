package com.example.LMS.mappers;

import com.example.LMS.dtos.GenderRequestDto;
import com.example.LMS.dtos.GenderResponseDto;
import com.example.LMS.models.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses= BookMapper.class)
public interface GenderMapper {

    @Mapping(source="name", target="name")
    Gender toEntity(GenderRequestDto dto);

    GenderResponseDto toDto(Gender gender);

    List<GenderResponseDto> toDtoList(List<Gender> genders);
}
