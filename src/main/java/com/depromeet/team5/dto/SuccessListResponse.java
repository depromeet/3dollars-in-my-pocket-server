package com.depromeet.team5.dto;

import com.depromeet.team5.domain.ResultCode;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class SuccessListResponse<T> extends ApiResponse {
    List<T> data;

    public SuccessListResponse(List<T> data) {
        super(ResultCode.SUCCESS);
        this.data = data;
    }
}

