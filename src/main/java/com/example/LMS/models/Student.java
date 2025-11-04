package com.example.LMS.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
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

    @Column(name="email", nullable= false, unique= true)
    private String email;

    @Column(name="phone_number", nullable= false, unique= true)
    private String phone;

    @OneToMany(mappedBy= "student", cascade= CascadeType.ALL, orphanRemoval = true )
    private List<Loan> loans;

}
