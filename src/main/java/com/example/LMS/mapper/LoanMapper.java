package com.example.LMS.mapper;

import com.example.LMS.domain.request.LoanRequestDto;
import com.example.LMS.domain.response.LoanResponseDto;
import com.example.LMS.domain.model.Loan;
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
    @Mapping(target="expectedReturnDate", source="expectedReturnDate")
    LoanResponseDto toDto(Loan loan);

    List<LoanResponseDto> toDtoList(List<Loan> loans);
}
