package com.depromeet.team5.util.auth;

import com.depromeet.team5.domain.User;
import com.depromeet.team5.model.DefaultRes;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthAspect {

    private final static String AUTHORIZATION = "Authorization";

    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().message("인증 실패").build();

    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final HttpServletRequest httpServletRequest;

    private final JwtService jwtService;

    private final LoginService loginService;

    @Around("@annotation(com.depromeet.team5.util.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        if (jwt == null) return RES_RESPONSE_ENTITY;

        final JwtService.Token token = jwtService.decode(jwt);

        if (token == null) {
            return RES_RESPONSE_ENTITY;
        } else {
            User user = loginService.userInfo(token.getUser_idx());

            if (user == null) return RES_RESPONSE_ENTITY;
            return pjp.proceed(pjp.getArgs());
        }
    }
}
