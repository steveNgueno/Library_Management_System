package com.example.LMS.services.impl;

import com.example.LMS.dtos.StudentRequestDto;
import com.example.LMS.dtos.StudentResponseDto;
import com.example.LMS.mappers.StudentMapper;
import com.example.LMS.models.Student;
import com.example.LMS.repositories.StudentRepository;
import com.example.LMS.services.StudentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponseDto save(StudentRequestDto request) {

        if (request == null) {
            throw new RuntimeException("Please fill all the fields");
        }

        Student student = studentRepository.save(studentMapper.toEntity(request));

        return studentMapper.toDto(student);
    }

    @Override
    public StudentResponseDto getById(Long id) {

        return studentMapper.toDto(findById(id));
    }

    @Override
    public List<StudentResponseDto> getAll() {
        return studentMapper.toDtoList(studentRepository.findAll());
    }

    @Override
    public StudentResponseDto update(Long id, StudentRequestDto request) {

        Student student = findById(id);

        Student updateStudent = Student
                .builder()
                .firstname(request.firstname().isBlank() ? student.getFirstname(): request.firstname())
                .lastname(request.lastname().isBlank() ? student.getLastname(): request.lastname())
                .birthday(request.birthday() == null ? student.getBirthday(): request.birthday())
                .email(student.getEmail())
                .phone(request.phone().isBlank() ? student.getPhone() : request.phone())
                .loans(student.getLoans())
                .build();


        return studentMapper.toDto(updateStudent);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        studentRepository.deleteById(id);
    }

    private Student findById(Long id){
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException(format("Student with this id: %s not found", id)));
    }
}
