package com.example.LMS.controller;

import com.example.LMS.domain.request.StudentRequestDto;
import com.example.LMS.domain.response.StudentResponseDto;
import com.example.LMS.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentResponseDto> saveStudent(@Valid @RequestBody StudentRequestDto request){
        return ResponseEntity.ok(studentService.save(request));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<StudentResponseDto>> getAllStudent(){
        return ResponseEntity.ok(studentService.getAll());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDto request){
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
