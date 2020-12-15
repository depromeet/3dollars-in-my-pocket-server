package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class WithdrawalUserException extends UnauthorizedException {

    public WithdrawalUserException(Long userId) {
        super(String.format("userId (%s)는 탈퇴한 사용자입니다.", userId));
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.USER_INVALID_STATUS_WITHDRAWAL);
    }
}