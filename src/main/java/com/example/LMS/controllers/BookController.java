package com.example.LMS.controllers;

import com.example.LMS.dtos.BookDto;
import com.example.LMS.services.impl.BookServiceImpl;
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
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto){

        BookDto dto = bookServiceImpl.saveBook(bookDto);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id){

        return ResponseEntity.ok(bookServiceImpl.getBookById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookDto>> getAllBooks(){

        return ResponseEntity.ok(bookServiceImpl.getAllBooks());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDto> updateBookById(@PathVariable Long id, @RequestBody BookDto dto){
        return ResponseEntity.ok(bookServiceImpl.updateBookById(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id){
        bookServiceImpl.deleteBookById(id);

        return ResponseEntity.noContent().build();
    }
}
