package com.depromeet.team5.exception;

import java.text.MessageFormat;

public class FaqTagRenameFailedException extends RuntimeException {
    private final Long faqTagId;
    private final String name;

    public FaqTagRenameFailedException(Long faqTagId, String name) {
        this.faqTagId = faqTagId;
        this.name = name;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(
                "이미 같은 이름의 FAQ Tag 가 존재합니다. 다른 이름으로 시도해주세요. faqTagId: {0}, name: {1}",
                faqTagId,
                name
        );
    }
}
