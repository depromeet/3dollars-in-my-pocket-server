package com.depromeet.team5.exception;

public class ReviewModifiedByNotAuthorException extends ForbiddenException {
    private static final long serialVersionUID = 2654920249050619591L;
    private static final String DEFAULT_MESSAGE = "리뷰 작성한 사람만 수정 및 삭제 할 수 있습니다.";

    public ReviewModifiedByNotAuthorException() {
        super(DEFAULT_MESSAGE);
    }

    public ReviewModifiedByNotAuthorException(String message) {
        super(message);
    }
}
