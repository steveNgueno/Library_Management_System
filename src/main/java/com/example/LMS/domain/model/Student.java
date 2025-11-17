package com.example.LMS.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="students")
public class Student extends User{

    @Column(name="student_id")
    private String studentId;

    @Column(name="sector")
    private String program;

    @OneToMany(mappedBy= "student", cascade= CascadeType.ALL, orphanRemoval = true )
    private List<Loan> loans;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval= true)
    private List<Notification> notifications;

}
