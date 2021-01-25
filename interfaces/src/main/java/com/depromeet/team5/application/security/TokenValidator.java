package com.depromeet.team5.application.security;

public interface TokenValidator {

    boolean supports(String accessToken);

    boolean isValid(String accessToken);
}
