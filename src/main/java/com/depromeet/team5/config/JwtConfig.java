package com.depromeet.team5.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.yml")
public class JwtConfig {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;


    @Bean
    public JWTVerifier jwtVerifier() {
        Verification verification = JWT.require(Algorithm.HMAC256(secret));
        return verification.build();
    }

    @Bean
    public JwtSettings jwtSettings() {
        return JwtSettings.of(issuer, secret);
    }

    @Getter
    public static class JwtSettings {
        private final String issuer;
        private final String secret;

        private JwtSettings(String issuer, String secret) {
            this.issuer = issuer;
            this.secret = secret;
        }

        public static JwtSettings of(String issuer, String secret) {
            return new JwtSettings(issuer, secret);
        }
    }

}

