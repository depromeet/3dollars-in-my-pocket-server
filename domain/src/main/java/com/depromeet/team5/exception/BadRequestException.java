package com.depromeet.team5.exception;

public abstract class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(message);
    }
}
