package com.example.LMS.controller;

import com.example.LMS.domain.model.RefreshToken;
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
        RefreshToken refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken.getToken()
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (refreshToken == null || !jwtService.isRefreshTokenValid(refreshToken)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or revoked refresh token"));
        }

        String userEmail = jwtService.validateTokenAndExtractUsername(refreshToken);

        if (userEmail == null || jwtService.isTokenExpired(refreshToken)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid refresh token"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken.getToken()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Refresh token required"));
        }

        jwtService.revokeRefreshToken(refreshToken);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }



}
