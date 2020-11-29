package com.depromeet.team5.exception;

public class InvalidAccessTokenException extends UnauthorizedException {
    public InvalidAccessTokenException() {
        super("AccessToken 이 유효하지 않습니다. ");
    }


}
