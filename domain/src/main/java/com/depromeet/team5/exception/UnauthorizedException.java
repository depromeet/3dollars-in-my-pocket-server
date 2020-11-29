package com.depromeet.team5.exception;

import org.springframework.http.HttpStatus;

public abstract class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
