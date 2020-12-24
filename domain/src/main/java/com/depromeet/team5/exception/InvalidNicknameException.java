package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class InvalidNicknameException extends ApplicationException {
    public InvalidNicknameException(String message) {
        super(message);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.BAD_REQUEST);
    }
}
