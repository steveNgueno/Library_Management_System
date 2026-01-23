package com.example.LMS.repository;

import com.example.LMS.domain.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByStaffId(String staffId);

    Optional<Admin> findByEmail(String username);
}
