package com.example.LMS.controllers;

import com.example.LMS.dtos.LoanRequestDto;
import com.example.LMS.dtos.LoanResponseDto;
import com.example.LMS.exceptions.BusinessLogicException;
import com.example.LMS.services.impl.LoanServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loan")
public class LoanController {

    private final LoanServiceImpl loanServiceImpl;

    @PostMapping("/subscribe")
    public ResponseEntity<LoanResponseDto> makeABorrow(@RequestBody LoanRequestDto request) throws BusinessLogicException {
        return ResponseEntity.ok(loanServiceImpl.makeABorrow(request));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id){
        return ResponseEntity.ok(loanServiceImpl.getLoanById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanResponseDto>> getLoanById(){
        return ResponseEntity.ok(loanServiceImpl.getAllLoans());
    }

    @GetMapping("/getAll/{email}")
    public ResponseEntity<List<LoanResponseDto>> getLoanById(@PathVariable String email){
        return ResponseEntity.ok(loanServiceImpl.getAllLoansByStudent(email));
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<LoanResponseDto> makeAReturn(@PathVariable Long id){
        return ResponseEntity.ok(loanServiceImpl.makeAReturn(id));
    }
}
