package com.depromeet.team5.dto;

import com.depromeet.team5.domain.ResultCode;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class SuccessSimpleResponse<T> extends ApiResponse {
    T data;

    public SuccessSimpleResponse(T data) {
        super(ResultCode.SUCCESS);
        this.data = data;
    }
}
