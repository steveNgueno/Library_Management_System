package com.example.LMS.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="genders")
public class Gender extends BaseEntity{

    @Column(name= "gender_name")
    private String name;

    @OneToMany(mappedBy = "genders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;
}
