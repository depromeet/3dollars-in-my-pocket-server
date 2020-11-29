package com.depromeet.team5.util.auth;

import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.InvalidAccessTokenException;
import com.depromeet.team5.exception.UserNotFoundException;
import com.depromeet.team5.exception.WithdrawalUserException;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthAspect {

    private static final String AUTHORIZATION = "Authorization";

    private final HttpServletRequest httpServletRequest;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Around("@annotation(com.depromeet.team5.util.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        if (jwt == null) {
            throw new InvalidAccessTokenException();
        }

        final JwtService.Token token = jwtService.decode(jwt);

        if (token == null) {
            return new InvalidAccessTokenException();
        } else {
            Long userId = token.getUserId();
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, HttpStatus.UNAUTHORIZED));
            if (user.isWithdrawal()) {
                throw new WithdrawalUserException(userId);
            }
            return pjp.proceed(pjp.getArgs());
        }
    }
}
