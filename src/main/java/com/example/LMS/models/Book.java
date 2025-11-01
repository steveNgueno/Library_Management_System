package com.example.LMS.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="books")
public class Book extends BaseEntity{

    @Column(name="title", nullable= false)
    private String title;

    @Column(name="author", nullable= false)
    private String author;

    @Column(name="publication_year", nullable= false)
    private LocalDate publicationYear;

    @Column(name="num_of_copies", nullable= false)
    private int numOfCopies;

    @ManyToOne()
    @JoinColumn(name= "gender_id", nullable= false)
    private Gender gender;

    @OneToMany(mappedBy= "loan_id", cascade= CascadeType.ALL, orphanRemoval= true)
    private List<Loan> loans;
}
