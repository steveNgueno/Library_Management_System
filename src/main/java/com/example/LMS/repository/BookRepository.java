package com.example.LMS.repository;

import com.example.LMS.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //boolean existsByTitle(String title);

    Optional<Book> findByTitle(String title);

    boolean existsByTitle(String title);
}
