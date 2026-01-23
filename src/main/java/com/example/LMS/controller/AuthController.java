package com.example.LMS.controller;

import com.example.LMS.domain.request.LoginRequest;
import com.example.LMS.domain.request.RefreshTokenRequest;
import com.example.LMS.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest credentials) {
        String email = credentials.email();
        String password = credentials.password();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        String userEmail = jwtService.validateTokenAndExtractUsername(refreshToken);

        if (userEmail == null || jwtService.isTokenExpired(refreshToken)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid refresh token"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);  // Rotate refresh too

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        ));
    }
}
