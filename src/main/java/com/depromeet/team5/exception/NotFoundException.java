package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public abstract class NotFoundException extends ApplicationException {
    protected NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.NOT_FOUND);
    }
}
