package com.depromeet.team5.dto;

import com.depromeet.team5.domain.ResultCode;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class SuccessPageResponse<T> extends ApiResponse {
    List<T> data;
    Long totalCount;

    public SuccessPageResponse(Page<T> data) {
        super(ResultCode.SUCCESS);
        this.data = data.getContent();
        this.totalCount = data.getTotalElements();
    }
}
