package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class FaqNotFoundException extends NotFoundException {
    public FaqNotFoundException(Long faqId) {
        super("FAQ 를 찾을 수 없습니다. faqId:" + faqId);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.FAQ_NOT_FOUND);
    }
}
