package com.example.LMS.repositories;

import com.example.LMS.models.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    boolean existsByName(String name);
}
