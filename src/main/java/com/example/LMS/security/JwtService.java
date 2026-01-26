package com.example.LMS.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.LMS.domain.model.Admin;
import com.example.LMS.domain.model.RefreshToken;
import com.example.LMS.domain.model.Student;
import com.example.LMS.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class JwtService {

    private JwtKeyProvider keyProvider;

    private final RefreshTokenRepository refreshTokenRepository;

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


    public RefreshToken generateRefreshToken(UserDetails userDetails){

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) keyProvider.getPublicKey(), (RSAPrivateKey) keyProvider.getPrivateKey());
        String refreshToken = JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .sign(algorithm);

        RefreshToken entity = RefreshToken.builder()
                .token(refreshToken)
                .student(userDetails instanceof Student ? (Student) userDetails : null )
                .admin(userDetails instanceof Admin ? (Admin) userDetails : null)
                .expiryDate(LocalDateTime.now().plus(Duration.ofMillis(REFRESH_TOKEN_EXPIRATION)))
                .revoked(false)
                .build();

        refreshTokenRepository.save(entity);

        return entity;

    }

    public boolean isRefreshTokenValid(String token){

        Optional<RefreshToken> rtOpt = refreshTokenRepository.findByToken(token);

        if(rtOpt.isEmpty()){
            return false;
        }
        RefreshToken rt = rtOpt.get();

        return !rt.isRevoked() && !rt.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public void revokeRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(rt -> {
                    rt.setRevoked(true);
                    refreshTokenRepository.save(rt);
                });
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

    @Scheduled(cron= "0 0 9 */7 * ?")
    public void cleanupExpiredAndRevokedRefreshToken(){

        int deleted = refreshTokenRepository.deleteByExpiryDateBeforeOrRevokedTrue(LocalDateTime.now());

        log.info("Weekly cleanup: removed {} expired or revoked refresh tokens", deleted);
    }

}
