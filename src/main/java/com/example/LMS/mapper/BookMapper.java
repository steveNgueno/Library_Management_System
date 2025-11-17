package com.example.LMS.mapper;

import com.example.LMS.domain.request.BookRequestDto;
import com.example.LMS.domain.response.BookResponseDto;
import com.example.LMS.domain.model.Book;
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
