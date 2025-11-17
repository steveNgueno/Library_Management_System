package com.example.LMS.domain.model;

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

    @Column(name="publication_year")
    private LocalDate publicationYear;

    @Column(name="num_of_copies", nullable= false)
    private int numOfCopies;

    @Column(name="available_copies")
    private int availableCopies;

    @ManyToOne()
    @JoinColumn(name= "gender_id")
    private Gender gender;

    @OneToMany(mappedBy= "book", cascade= CascadeType.ALL, orphanRemoval= true)
    private List<Loan> loans;
}
