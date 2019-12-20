package com.depromeet.team5.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.depromeet.team5.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.auth0.jwt.JWT.require;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig.JwtSettings jwtSettings;

    public String create(final long user_idx) {
        try {
            JWTCreator.Builder builder = JWT.create();
            builder.withIssuer(jwtSettings.getIssuer());
            builder.withClaim("ID", user_idx);
            return builder.sign(Algorithm.HMAC256(jwtSettings.getSecret()));
        } catch (JWTCreationException JwtCreationException) {
            log.info(JwtCreationException.getMessage());
        }
        return null;
    }


    public Token decode(final String token) {
        try {
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(jwtSettings.getSecret())).withIssuer(jwtSettings.getIssuer()).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return new Token(decodedJWT.getClaim("ID").asLong());
        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static class Token {
        private long user_idx = -1;

        public Token() {
        }

        public Token(final long user_idx) {
            this.user_idx = user_idx;
        }

        public long getUser_idx() {
            return user_idx;
        }
    }

    public static class TokenRes {
        private String token;

        public TokenRes() {
        }

        public TokenRes(final String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
