package com.example.LMS.controller;

import com.example.LMS.domain.request.BookRequestDto;
import com.example.LMS.domain.response.BookResponseDto;
import com.example.LMS.service.impl.BookServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookServiceImpl bookServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto request){

        BookResponseDto dto = bookServiceImpl.saveBook(request);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id){

        return ResponseEntity.ok(bookServiceImpl.getBookById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){

        return ResponseEntity.ok(bookServiceImpl.getAllBooks());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookResponseDto> updateBookById(@PathVariable Long id, @RequestBody BookRequestDto dto){
        return ResponseEntity.ok(bookServiceImpl.updateBookById(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id){
        bookServiceImpl.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }
}
