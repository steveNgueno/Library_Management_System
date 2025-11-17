package com.example.LMS.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="admins")
public class Admin extends User{

    @Column(name="staff_id")
    private String staffId;

    @Column(name="position")
    private String position;
}
