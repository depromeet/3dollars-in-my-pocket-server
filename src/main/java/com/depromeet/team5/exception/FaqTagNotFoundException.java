package com.depromeet.team5.exception;

public class FaqTagNotFoundException extends NotFoundException {
    private final Long faqTagId;

    public FaqTagNotFoundException(Long faqTagId) {
        super();
        this.faqTagId = faqTagId;
    }

    @Override
    public String getMessage() {
        return "FAQ Tag 를 찾을 수 없습니다. faqTagId:" + faqTagId;
    }
}
