package com.depromeet.team5.exception;

public class NickNameCheckException extends RuntimeException {
    public NickNameCheckException() {
        super("같은 닉네임이 존재합니다.");
    }
}
