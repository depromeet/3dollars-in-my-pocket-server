package com.depromeet.team5.application.security;

public interface TokenService<T> {
    String create(T value);

    T decode(String accessToken);
}
