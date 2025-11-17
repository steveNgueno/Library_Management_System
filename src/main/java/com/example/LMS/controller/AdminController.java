package com.example.LMS.controller;

import com.example.LMS.domain.request.AdminRequestDto;
import com.example.LMS.domain.response.AdminResponseDto;
import com.example.LMS.service.impl.AdminServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<AdminResponseDto> saveStudent(@Valid @RequestBody AdminRequestDto request){
        return ResponseEntity.ok(adminServiceImpl.save(request));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<AdminResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(adminServiceImpl.getById(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<AdminResponseDto>> getAllStudent(){
        return ResponseEntity.ok(adminServiceImpl.getAll());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<AdminResponseDto> updateStudent(@PathVariable Long id, @RequestBody AdminRequestDto request){
        return ResponseEntity.ok(adminServiceImpl.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        adminServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}
