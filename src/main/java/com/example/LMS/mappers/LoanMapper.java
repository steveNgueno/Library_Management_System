package com.example.LMS.mappers;

import com.example.LMS.dtos.LoanDto;
import com.example.LMS.models.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan toEntity(LoanDto loanDto);

    LoanDto toDto(Loan loan);
}
