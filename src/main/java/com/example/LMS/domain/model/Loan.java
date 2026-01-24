package com.example.LMS.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="loans")
public class Loan extends BaseEntity{

    @Column(name="loan_date", nullable= false, updatable= false)
    private LocalDate loanDate;

    @Column(name="return_date")
    private LocalDate returnDate;

    @Column(name = "expected_return_date", nullable = false, updatable= false)
    private LocalDate expectedReturnDate;

    @Column(name="is_active")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id" , nullable= false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", nullable= false)
    private Student student;

}
