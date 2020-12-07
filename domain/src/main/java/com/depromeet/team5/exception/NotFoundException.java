package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public abstract class NotFoundException extends ApplicationException {
    protected NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.NOT_FOUND);
    }
}
