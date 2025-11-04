package com.example.LMS.controllers;

import com.example.LMS.dtos.StudentRequestDto;
import com.example.LMS.dtos.StudentResponseDto;
import com.example.LMS.services.impl.StudentServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<StudentResponseDto> saveStudent(@Valid @RequestBody StudentRequestDto request){
        return ResponseEntity.ok(studentServiceImpl.save(request));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentServiceImpl.getById(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<StudentResponseDto>> getAllStudent(){
        return ResponseEntity.ok(studentServiceImpl.getAll());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDto request){
        return ResponseEntity.ok(studentServiceImpl.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        studentServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }

}
