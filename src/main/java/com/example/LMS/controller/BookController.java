package com.example.LMS.controller;

import com.example.LMS.domain.request.BookRequestDto;
import com.example.LMS.domain.response.BookResponseDto;
import com.example.LMS.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto request){

        BookResponseDto dto = bookService.saveBook(request);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id){

        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){

        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookResponseDto> updateBookById(@PathVariable Long id, @RequestBody BookRequestDto dto){
        return ResponseEntity.ok(bookService.updateBookById(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }
}
