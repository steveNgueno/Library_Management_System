package com.example.LMS.mappers;

import com.example.LMS.dtos.LoanRequestDto;
import com.example.LMS.dtos.LoanResponseDto;
import com.example.LMS.models.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source="bookTitle", target="book.title")
    @Mapping(source="studentEmail", target="student.email")
    Loan toEntity(LoanRequestDto request);

    @Mapping(target="bookTitle", source="book.title")
    @Mapping(target="emailStudent", source="student.email")
    @Mapping(target="active", source="active")
    LoanResponseDto toDto(Loan loan);

    List<LoanResponseDto> toDtoList(List<Loan> loans);
}
