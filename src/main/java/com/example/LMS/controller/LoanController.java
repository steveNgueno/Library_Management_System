package com.example.LMS.controller;

import com.example.LMS.domain.request.LoanRequestDto;
import com.example.LMS.domain.response.LoanResponseDto;
import com.example.LMS.exception.BusinessLogicException;
import com.example.LMS.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loan")
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/subscribe")
    public ResponseEntity<LoanResponseDto> makeABorrow(@Valid @RequestBody LoanRequestDto request) throws BusinessLogicException {
        return ResponseEntity.ok(loanService.makeABorrow(request));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Long id){
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanResponseDto>> getLoanById(){
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/getAll/{email}")
    public ResponseEntity<List<LoanResponseDto>> getLoanById(@PathVariable String email){
        return ResponseEntity.ok(loanService.getAllLoansByStudent(email));
    }

    @PutMapping("/return")
    public ResponseEntity<LoanResponseDto> makeAReturn(@Valid @RequestBody LoanRequestDto request){
        return ResponseEntity.ok(loanService.makeAReturn(request));
    }
}
