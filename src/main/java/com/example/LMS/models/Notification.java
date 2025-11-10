package com.example.LMS.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Notification extends BaseEntity {

    @Column(name= "title")
    private String title;

    @Column(name= "message")
    private String message;

    @ManyToOne
    @JoinColumn(name= "student_id")
    private Student student;

}
