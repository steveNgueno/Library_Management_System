package com.example.LMS.repository;

import com.example.LMS.domain.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    boolean existsByName(String name);
}
