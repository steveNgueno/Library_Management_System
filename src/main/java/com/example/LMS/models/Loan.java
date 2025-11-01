package com.example.LMS.models;

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

    @Column(name="loan_date", nullable= false)
    private LocalDate LoanDate;

    @Column(name="return_date", nullable= false)
    private LocalDate returnDate;

    @ManyToOne()
    @JoinColumn(name="book_id" , nullable= false)
    private Book book;

    @ManyToOne()
    @JoinColumn(name="student_id", nullable= false)
    private Student student;
}
