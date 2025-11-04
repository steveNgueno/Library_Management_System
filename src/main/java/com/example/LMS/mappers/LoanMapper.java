package com.example.LMS.mappers;


import com.example.LMS.dtos.LoanRequestDto;
import com.example.LMS.dtos.LoanResponseDto;
import com.example.LMS.models.Loan;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan toEntity(LoanRequestDto request);

    LoanResponseDto toDto(Loan loan);

    List<LoanResponseDto> toDtoList(List<Loan> loans);
}
