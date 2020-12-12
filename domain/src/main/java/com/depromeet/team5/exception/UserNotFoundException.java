package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(Long userId) {
        super("유저를 찾을 수 없습니다. userId: " + userId);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.USER_NOT_FOUND);
    }
}