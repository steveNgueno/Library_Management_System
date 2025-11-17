package com.example.LMS.service;

import com.example.LMS.domain.request.LoanRequestDto;
import com.example.LMS.domain.response.LoanResponseDto;
import com.example.LMS.exception.BusinessLogicException;


import java.util.List;

public interface LoanService {

    LoanResponseDto makeABorrow(LoanRequestDto request) throws BusinessLogicException;

    LoanResponseDto getLoanById(Long id);

    List<LoanResponseDto> getAllLoans();

    LoanResponseDto makeAReturn(LoanRequestDto request);

    List<LoanResponseDto> getAllLoansByStudent(String email);

    void deleteById(Long id);
}
