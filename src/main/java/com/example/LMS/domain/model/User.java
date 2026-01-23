package com.example.LMS.domain.model;

import com.example.LMS.domain.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@MappedSuperclass
@Getter
@Setter
public abstract class User extends BaseEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

}
