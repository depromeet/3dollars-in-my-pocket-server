package com.depromeet.team5.exception;

public class FailedToParseJwtException extends ApplicationException{
    public FailedToParseJwtException(){
        super("Json Web Token 파싱에 실패했습니다.");
    }
}
