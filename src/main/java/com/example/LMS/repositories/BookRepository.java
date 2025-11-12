package com.example.LMS.repositories;

import com.example.LMS.models.Book;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //boolean existsByTitle(String title);

    Optional<Book> findByTitle(String title);

    boolean existsByTitle(String title);
}
