package com.depromeet.team5.dto;

import com.depromeet.team5.domain.ResultCode;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class FailureResponse<T> extends ApiResponse {
    String message;

    public FailureResponse(ResultCode resultCode) {
        super(resultCode);
        this.message = resultCode.getDefaultMessage();
    }

    public FailureResponse(ResultCode resultCode, String message) {
        super(resultCode);
        this.message = message;
    }
}
