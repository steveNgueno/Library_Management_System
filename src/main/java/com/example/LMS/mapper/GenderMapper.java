package com.example.LMS.mapper;

import com.example.LMS.domain.request.GenderRequestDto;
import com.example.LMS.domain.response.GenderResponseDto;
import com.example.LMS.domain.model.Gender;
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
