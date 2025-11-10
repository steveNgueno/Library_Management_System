package com.example.LMS.repositories;

import com.example.LMS.models.Book;
import com.example.LMS.models.Loan;
import com.example.LMS.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> getAllLoansByStudent(Student student);

    Optional<Loan> findByStudentAndBookAndActive(Student student, Book book, boolean isActive);

    List<Loan> findByExpectedReturnDateBeforeAndActive(LocalDate currentDate, boolean active);
}
