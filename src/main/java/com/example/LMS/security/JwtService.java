package com.example.LMS.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@AllArgsConstructor
@Service
@Slf4j
public class JwtService {

    private JwtKeyProvider keyProvider;

    private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    public String generateAccessToken(UserDetails userDetails){

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) keyProvider.getPublicKey(), (RSAPrivateKey) keyProvider.getPrivateKey());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .sign(algorithm);
    }


    public String generateRefreshToken(UserDetails userDetails){

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) keyProvider.getPublicKey(), (RSAPrivateKey) keyProvider.getPrivateKey());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(algorithm);
    }

    public String validateTokenAndExtractUsername(String token){
        try{
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) keyProvider.getPublicKey(), null);
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e){
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean isTokenExpired(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(new Date());
    }

}
