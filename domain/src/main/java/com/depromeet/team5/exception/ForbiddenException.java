package com.depromeet.team5.exception;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends ApplicationException {
    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
