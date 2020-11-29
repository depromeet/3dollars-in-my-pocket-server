package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public interface ResultCodeSupport {
    default Optional<ResultCode> getResultCode() {
        return Optional.empty();
    }
}
