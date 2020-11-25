package com.depromeet.team5.exception;

public class WithdrawalUserException extends RuntimeException {
    public WithdrawalUserException(Long userId) {
        super(String.format("userId (%s)는 탈퇴한 사용자입니다.", userId));
    }
}