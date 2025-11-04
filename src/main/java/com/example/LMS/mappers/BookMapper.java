package com.example.LMS.mappers;

import com.example.LMS.dtos.BookRequestDto;
import com.example.LMS.dtos.BookResponseDto;
import com.example.LMS.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel ="spring", uses = LoanMapper.class)
public interface BookMapper {

    Book toEntity(BookRequestDto request);

    @Mapping(target = "genderName", source = "gender.name")
    BookResponseDto toDto(Book book);

    List<BookResponseDto> toDtoList(List<Book> books);
}
