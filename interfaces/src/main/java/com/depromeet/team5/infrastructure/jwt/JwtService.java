package com.depromeet.team5.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.depromeet.team5.application.security.TokenService;
import com.depromeet.team5.exception.InvalidAccessTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService implements TokenService<Long> {
    public static final String CLAIM_USER_ID = "ID";
    private final JwtSettings jwtSettings;
    private final JWTVerifier jwtVerifier;

    @Override
    public String create(Long userId) {
        Assert.notNull(userId, "'userId' must not be null");
        return JWT.create()
                .withIssuer(jwtSettings.getIssuer())
                .withClaim(CLAIM_USER_ID, userId)
                .sign(Algorithm.HMAC256(jwtSettings.getSecret()));
    }

    @Override
    public Long decode(final String accessToken) {
        Assert.notNull(accessToken, "'accessToken' must not be null");
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            Long userId = decodedJWT.getClaim(CLAIM_USER_ID).asLong();
            Assert.notNull(userId, "'userId' must not be null");
            return userId;
        } catch (Exception e) {
            throw new InvalidAccessTokenException("Failed to decode accessToken. token: " + accessToken, e);
        }
    }
}
