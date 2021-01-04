package com.depromeet.team5.application.security;

public interface TokenVerifier {
    boolean isVerified(String accessToken);
}
