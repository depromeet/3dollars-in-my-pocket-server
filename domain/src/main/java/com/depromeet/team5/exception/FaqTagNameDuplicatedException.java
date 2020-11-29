package com.depromeet.team5.exception;

import com.depromeet.team5.domain.ResultCode;

import java.text.MessageFormat;
import java.util.Optional;

public class FaqTagNameDuplicatedException extends BadRequestException {
    public FaqTagNameDuplicatedException(Long faqTagId, String name) {
        super(MessageFormat.format(
                "이미 같은 이름의 FAQ Tag 가 존재합니다. 다른 이름으로 시도해주세요. faqTagId: {0}, name: {1}",
                faqTagId,
                name
        ));
    }

    @Override
    public Optional<ResultCode> getResultCode() {
        return Optional.of(ResultCode.FAQ_TAG_NAME_DUPLICATED);
    }
}
