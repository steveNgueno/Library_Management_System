package com.example.LMS.mapper;


import com.example.LMS.domain.request.StudentRequestDto;
import com.example.LMS.domain.response.StudentResponseDto;
import com.example.LMS.domain.model.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = LoanMapper.class)
public interface StudentMapper {

    Student toEntity(StudentRequestDto StudentDto);

    StudentResponseDto toDto(Student student);

    List<StudentResponseDto> toDtoList(List<Student> student);

}
