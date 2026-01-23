package com.example.LMS.security;

import com.example.LMS.domain.model.User;
import com.example.LMS.repository.AdminRepository;
import com.example.LMS.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private StudentRepository studentRepository;
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<? extends User> userOpt = studentRepository.findByEmail(username);

        if(userOpt.isEmpty()){
            userOpt = adminRepository.findByEmail(username);
        }

        return userOpt.orElseThrow(() -> {
                log.debug("Login attempt for unknown email");
               return new UsernameNotFoundException("User not found with this email");
            }
        );
    }

}
