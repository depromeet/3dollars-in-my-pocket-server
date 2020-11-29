package com.depromeet.team5.exception;


import com.depromeet.team5.domain.ResultCode;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class UserNotFoundException extends NotFoundException {
    private final HttpStatus httpStatus;

    public UserNotFoundException(Long userId){
        super("유저를 찾을 수 없습니다. userId: " + userId);
        this.httpStatus = super.getHttpStatus();
    }

    public UserNotFoundException(Long userId, HttpStatus httpStatus){
        super("유저를 찾을 수 없습니다. userId: " + userId);
        this.httpStatus = httpStatus;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.USER_NOT_FOUND);
    }
}
