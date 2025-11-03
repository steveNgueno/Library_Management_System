package com.example.LMS.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="students")
public class Student extends BaseEntity{

    @Column(name="firstname", nullable= false)
    private String firstname;

    @Column(name="lastname", nullable= false)
    private String lastname;

    @Column(name="birthday", nullable= false)
    private LocalDate birthday;

    @Column(name="email", nullable= false)
    private String email;

    @OneToMany(mappedBy= "student", cascade= CascadeType.ALL, orphanRemoval = true )
    private List<Loan> loans;

}
