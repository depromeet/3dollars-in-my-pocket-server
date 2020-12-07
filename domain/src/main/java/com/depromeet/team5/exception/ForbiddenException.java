package com.depromeet.team5.exception;


public abstract class ForbiddenException extends ApplicationException {
    public ForbiddenException(String message) {
        super(message);
    }
}
