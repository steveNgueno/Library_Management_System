package com.example.LMS.controller;

import com.example.LMS.domain.request.AdminRequestDto;
import com.example.LMS.domain.response.AdminResponseDto;
import com.example.LMS.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<AdminResponseDto> saveStudent(@Valid @RequestBody AdminRequestDto request){
        return ResponseEntity.ok(adminService.save(request));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<AdminResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getById(id));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<AdminResponseDto>> getAllStudent(){
        return ResponseEntity.ok(adminService.getAll());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<AdminResponseDto> updateStudent(@PathVariable Long id, @RequestBody AdminRequestDto request){
        return ResponseEntity.ok(adminService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
