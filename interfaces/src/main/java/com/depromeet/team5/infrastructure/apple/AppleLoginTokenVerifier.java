package com.depromeet.team5.infrastructure.apple;

import com.depromeet.team5.application.security.TokenValidator;
import com.depromeet.team5.application.security.TokenVerifier;
import com.depromeet.team5.exception.FailedToParseJwtException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleLoginTokenVerifier implements TokenVerifier {

    @Value("${apple.base_url")
    private String appleBaseUrl;
    @Value("${apple.client_id}")
    private String appleClientId;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private ApplePublicKeyResponse getApplePublicKey() {
        return webClient.mutate()
                .baseUrl(appleBaseUrl)
                .build()
                .get()
                .uri("/auth/keys")
                .retrieve()
                .bodyToMono(ApplePublicKeyResponse.class)
                .block();
    }

    private Claims getClaims(String token) {
        try {
            ApplePublicKeyResponse response = getApplePublicKey();
            String tokenHeader = token.substring(0, token.indexOf("."));
            String decodedTokenHeader = new String(Base64.getDecoder().decode(tokenHeader), StandardCharsets.UTF_8);

            @SuppressWarnings("unchecked")
            Map<String, String> header = objectMapper.readValue(decodedTokenHeader, Map.class);
            ApplePublicKeyResponse.Key key = response.getMatchedKey(header.get("kid"), header.get("alg"))
                    .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();

        } catch (JsonProcessingException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new FailedToParseJwtException();
        }
    }

    @Override
    public boolean isVerified(String accessToken) {
        Claims claim = getClaims(accessToken);
        return claim != null
                && appleBaseUrl.equals(claim.getIssuer())
                && appleClientId.equals(claim.getAudience())
                && claim.getExpiration().after(new Date());
    }
}
