package com.depromeet.team5.dto;

import com.depromeet.team5.domain.ResultCode;
import lombok.Getter;

@Getter
public abstract class ApiResponse {

    private final ResultCode resultCode;

    protected ApiResponse(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
