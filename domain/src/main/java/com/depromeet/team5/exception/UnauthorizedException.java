package com.depromeet.team5.exception;

public abstract class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
