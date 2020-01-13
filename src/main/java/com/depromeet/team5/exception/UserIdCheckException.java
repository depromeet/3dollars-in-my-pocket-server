package com.depromeet.team5.exception;

public class UserIdCheckException extends RuntimeException {
    public UserIdCheckException() {
        super("이미 삭제 요청을 한 사용자입니다.");
    }
}
