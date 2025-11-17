package com.example.LMS.domain.model;

import com.example.LMS.domain.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class User extends BaseEntity{

    @Column(name="firstname", nullable= false)
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="birthday", nullable= false)
    private LocalDate birthday;

    @Column(name="email", nullable= false, unique= true)
    private String email;

    @Column(name="phone_number", nullable= false, unique= true)
    private String phone;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="password")
    private String password;

}
