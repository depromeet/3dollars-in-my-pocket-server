package com.depromeet.team5.controller;

import com.depromeet.team5.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

@ControllerAdvice
@RequiredArgsConstructor
@PropertySource("classpath:key.properties")
public class ApiControllerAdvice {
    private final JwtService jwtService;

    @Value("${key.admin}")
    private String key;

    @ModelAttribute
    public Long getUserId(@RequestHeader(required = false) String authorization) {
        if (authorization == null) {
            return null;
        }
        if (authorization.equals("KakaoAK " + key)) {
            return null;
        }
        return jwtService.decode(authorization).getUserId();
    }
}
