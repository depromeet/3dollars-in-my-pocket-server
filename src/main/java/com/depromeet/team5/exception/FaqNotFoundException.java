package com.depromeet.team5.exception;

public class FaqNotFoundException extends NotFoundException {
    private final Long faqId;

    public FaqNotFoundException(Long faqId) {
        this.faqId = faqId;
    }

    @Override
    public String getMessage() {
        return "FAQ 를 찾을 수 없습니다. faqId:" + faqId;
    }
}
