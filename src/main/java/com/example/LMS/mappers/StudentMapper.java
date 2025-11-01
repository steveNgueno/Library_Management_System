package com.example.LMS.mappers;

import com.example.LMS.dtos.StudentDto;
import com.example.LMS.models.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = LoanMapper.class)
public interface StudentMapper {

    Student toEntity(StudentDto StudentDto);

    StudentDto toDto(Student student);


}
