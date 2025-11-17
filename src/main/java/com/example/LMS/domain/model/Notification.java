package com.example.LMS.domain.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="notifications")
public class Notification extends BaseEntity {

    @Column(name= "title")
    private String title;

    @Column(name= "message")
    private String message;

    @ManyToOne
    @JoinColumn(name= "student_id")
    private Student student;

}
