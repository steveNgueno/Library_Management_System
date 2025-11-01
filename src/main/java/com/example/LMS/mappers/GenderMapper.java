package com.example.LMS.mappers;

import com.example.LMS.dtos.GenderDto;
import com.example.LMS.models.Gender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses= BookMapper.class)
public interface GenderMapper {

    Gender toEntity(GenderDto genderDto);

    GenderDto toDto(Gender gender);

}
