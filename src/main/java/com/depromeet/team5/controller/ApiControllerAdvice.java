package com.depromeet.team5.controller;

import com.depromeet.team5.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiControllerAdvice {
    private final JwtService jwtService;

    @ModelAttribute
    public Long getUserId(@RequestHeader(required = false) String authorization) {
        if (authorization == null) {
            return null;
        }
        return jwtService.decode(authorization).getUserId();
    }
}
