package com.example.LMS.controllers;

import com.example.LMS.dtos.GenderRequestDto;
import com.example.LMS.dtos.GenderResponseDto;
import com.example.LMS.services.impl.GenderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gender")
@RequiredArgsConstructor
public class GenderController {

    private final GenderServiceImpl genderServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<GenderResponseDto> saveGender(@Valid @RequestBody GenderRequestDto request){

         GenderResponseDto gender = genderServiceImpl.save(request);

        return ResponseEntity.ok(gender);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<GenderResponseDto> getGenderById(@PathVariable Long id){

        return ResponseEntity.ok(genderServiceImpl.getById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GenderResponseDto>> getAll(){
        return ResponseEntity.ok(genderServiceImpl.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGender(@PathVariable Long id){
        genderServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
