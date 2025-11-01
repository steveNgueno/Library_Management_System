package com.example.LMS.mappers;

import com.example.LMS.dtos.BookDto;
import com.example.LMS.models.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel ="spring", uses = LoanMapper.class)
public interface BookMapper {

    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);

    List<BookDto> toDtoList(List<Book> books);
}
