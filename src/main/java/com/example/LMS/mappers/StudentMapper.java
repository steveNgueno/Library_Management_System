package com.example.LMS.mappers;


import com.example.LMS.dtos.StudentRequestDto;
import com.example.LMS.dtos.StudentResponseDto;
import com.example.LMS.models.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = LoanMapper.class)
public interface StudentMapper {

    Student toEntity(StudentRequestDto StudentDto);

    StudentResponseDto toDto(Student student);

    List<StudentResponseDto> toDtoList(List<Student> student);

}
