package com.depromeet.team5.exception;

public abstract class ApplicationException extends RuntimeException implements HttpStatusSupport, ResultCodeSupport {
    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }
}
