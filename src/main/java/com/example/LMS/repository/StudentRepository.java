package com.example.LMS.repository;

import com.example.LMS.domain.model.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    Optional<Student> findByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByIdAndLoansActive(Long id, boolean active);

    boolean existsByStudentId(String studentId);

}
