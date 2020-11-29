package com.depromeet.team5.exception;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
