package com.example.LMS.repositories;

import com.example.LMS.models.Loan;
import com.example.LMS.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> getAllLoansByStudent(Student student);
}
