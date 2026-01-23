package com.example.LMS.controller;

import com.example.LMS.domain.request.GenderRequestDto;
import com.example.LMS.domain.response.GenderResponseDto;
import com.example.LMS.service.GenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gender")
@RequiredArgsConstructor
public class GenderController {

    private final GenderService genderService;

    @PostMapping("/save")
    public ResponseEntity<GenderResponseDto> saveGender(@Valid @RequestBody GenderRequestDto request){

         GenderResponseDto gender = genderService.save(request);

        return ResponseEntity.ok(gender);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<GenderResponseDto> getGenderById(@PathVariable Long id){

        return ResponseEntity.ok(genderService.getById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GenderResponseDto>> getAll(){
        return ResponseEntity.ok(genderService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGender(@PathVariable Long id){
        genderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
