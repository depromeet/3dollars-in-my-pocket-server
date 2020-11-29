package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException(Long reviewId) { super("해당하는 리뷰를 찾을 수 없습니다. reviewId" + reviewId); }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.REVIEW_NOT_FOUND);
    }
}
