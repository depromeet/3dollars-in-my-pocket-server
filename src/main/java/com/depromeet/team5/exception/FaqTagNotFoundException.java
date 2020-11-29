package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.util.Optional;

public class FaqTagNotFoundException extends NotFoundException {
    public FaqTagNotFoundException(Long faqTagId) {
        super("FAQ Tag 를 찾을 수 없습니다. faqTagId:" + faqTagId);
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.FAQ_TAG_NOT_FOUND);
    }
}
