package com.depromeet.team5.exception;

public abstract class ApplicationException extends RuntimeException implements ResultCodeSupport {
    public ApplicationException() { }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
