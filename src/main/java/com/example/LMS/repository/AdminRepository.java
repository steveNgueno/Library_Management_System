package com.example.LMS.repository;

import com.example.LMS.domain.model.Admin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByStaffId(String staffId);
}
