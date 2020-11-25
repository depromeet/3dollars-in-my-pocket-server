package com.depromeet.team5.exception;

public class DeleteUserException extends RuntimeException {
    public DeleteUserException() {
        super("탈퇴한 사용자입니다.");
    }
}