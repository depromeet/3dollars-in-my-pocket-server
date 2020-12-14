package com.depromeet.team5.util.auth;

import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.ForbiddenException;
import com.depromeet.team5.exception.InvalidAccessTokenException;
import com.depromeet.team5.exception.UnauthorizedException;
import com.depromeet.team5.exception.WithdrawalUserException;
import com.depromeet.team5.repository.UserRepository;
import com.depromeet.team5.service.JwtService;
import com.depromeet.team5.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class AuthAspect {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Around("@annotation(com.depromeet.team5.util.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String accessToken = RequestUtils.getHttpServletRequest()
                .map(it -> it.getHeader(AUTHORIZATION))
                .orElseThrow(InvalidAccessTokenException::new);

        final JwtService.Token token = jwtService.decode(accessToken);

        if (token == null) {
            return new InvalidAccessTokenException();
        } else {
            Long userId = token.getUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ForbiddenException("탈퇴한 회원입니다. userId:" + userId) {
            });
            if (user.isWithdrawal()) {
                throw new WithdrawalUserException(userId);
            }
            return pjp.proceed(pjp.getArgs());
        }
    }
}
