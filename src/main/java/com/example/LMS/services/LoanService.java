package com.example.LMS.services;

import com.example.LMS.dtos.LoanRequestDto;
import com.example.LMS.dtos.LoanResponseDto;
import com.example.LMS.exceptions.BusinessLogicException;


import java.util.List;

public interface LoanService {

    LoanResponseDto makeABorrow(LoanRequestDto request) throws BusinessLogicException;

    LoanResponseDto getLoanById(Long id);

    List<LoanResponseDto> getAllLoans();

    LoanResponseDto makeAReturn(Long id);

    List<LoanResponseDto> getAllLoansByStudent(String email);

    void deleteById(Long id);
}
