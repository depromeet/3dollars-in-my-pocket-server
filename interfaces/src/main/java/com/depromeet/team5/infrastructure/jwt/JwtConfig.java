package com.depromeet.team5.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtSettings jwtSettings() {
        return JwtSettings.of(issuer, secret);
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build();
    }
}

