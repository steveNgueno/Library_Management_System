package com.example.LMS.service.impl;

import com.example.LMS.domain.request.StudentRequestDto;
import com.example.LMS.domain.response.StudentResponseDto;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.exception.UserNotFoundException;
import com.example.LMS.mapper.StudentMapper;
import com.example.LMS.domain.model.Student;
import com.example.LMS.repository.AdminRepository;
import com.example.LMS.repository.StudentRepository;
import com.example.LMS.service.StudentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponseDto save(StudentRequestDto request) {

        if(studentRepository.existsByEmail(request.email()) || adminRepository.existsByEmail(request.email())){
            throw new BusinessLogicException("this email already exists","EMAIL_ALREADY_EXISTS");
        }

        if(adminRepository.existsByPhone(request.phone()) || studentRepository.existsByPhone(request.phone())){
            throw new BusinessLogicException("this phone number already exists", "PHONE_ALREADY_EXISTS");
        }

        if(studentRepository.existsByStudentId(request.studentId())){
            throw new BusinessLogicException("this student ID already exists", "STUDENT_ID_ALREADY_EXISTS");
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

        student.setFirstname(request.firstname() == null ||request.firstname().isBlank() ? student.getFirstname(): request.firstname());
        student.setLastname(request.lastname() == null || request.lastname().isBlank() ? student.getLastname(): request.lastname());
        student.setBirthday(request.birthday() == null ? student.getBirthday(): request.birthday());
        student.setPhone(request.phone() == null || request.phone().isBlank() ? student.getPhone() : request.phone());
        student.setStudentId(request.studentId() == null || request.studentId().isBlank() ? student.getStudentId() : request.studentId());
        student.setProgram(request.program() == null || request.program().isBlank() ? student.getProgram() : request.program());


        return studentMapper.toDto(studentRepository.save(student));
    }

    @Override
    public void delete(Long id) {

        if(studentRepository.existsByIdAndLoansActive(id, true)){
            throw new BusinessLogicException("This student can't be removed because he has an outstanding loan","NOT-REMOVABLE_STUDENT");
        }

        studentRepository.deleteById(id);
    }

    private Student findById(Long id){
        return studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
