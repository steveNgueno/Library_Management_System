package com.example.LMS.service.impl;

import com.example.LMS.domain.Enum.Action;
import com.example.LMS.domain.Enum.Role;
import com.example.LMS.domain.Enum.Status;
import com.example.LMS.domain.request.StudentRequestDto;
import com.example.LMS.domain.response.StudentResponseDto;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.exception.UserNotFoundException;
import com.example.LMS.mapper.StudentMapper;
import com.example.LMS.domain.model.Student;
import com.example.LMS.repository.AdminRepository;
import com.example.LMS.repository.StudentRepository;
import com.example.LMS.service.HistoryService;
import com.example.LMS.service.StudentService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final HistoryService historyService;
    private final PasswordEncoder passwordEncoder;


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

        Student student = studentMapper.toEntity(request);

        student.setPassword(passwordEncoder.encode(request.password()));
        student.setRole(Role.STUDENT);

        Student savedStudent = studentRepository.save(student);

        historyService.create(Status.SUCCESS, Action.ADD_USER,String.format("User %s has been added",request.firstname()));

        return studentMapper.toDto(savedStudent);
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

        Student savedStudent = studentRepository.save(student);

        historyService.create(Status.SUCCESS, Action.UPDATE_USER,String.format("User's infos %s have been updated",request.firstname()));
        return studentMapper.toDto(savedStudent);
    }

    @Override
    public void delete(Long id) {

        Student student = findById(id);

        if(studentRepository.existsByIdAndLoansActive(id, true)){

            historyService.create(Status.FAILED, Action.REMOVE_USER,String.format("Failed to remove %s %s because he has an outstanding loan",student.getFirstname(),student.getLastname()));

            throw new BusinessLogicException("This student can't be removed because he has an outstanding loan","NOT-REMOVABLE_STUDENT");
        }

        studentRepository.deleteById(id);

        historyService.create(Status.SUCCESS, Action.REMOVE_USER,String.format("User %s %s has been removed",student.getFirstname(),student.getLastname()));
    }

    private Student findById(Long id){
        return studentRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
