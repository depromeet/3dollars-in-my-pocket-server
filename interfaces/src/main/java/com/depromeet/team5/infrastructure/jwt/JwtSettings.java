package com.depromeet.team5.infrastructure.jwt;

import lombok.Value;

@Value(staticConstructor = "of")
public class JwtSettings {
    String issuer;
    String secret;
}
