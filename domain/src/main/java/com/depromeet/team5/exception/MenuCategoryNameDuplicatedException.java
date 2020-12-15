package com.depromeet.team5.exception;

public class MenuCategoryNameDuplicatedException extends BadRequestException {
    private static final long serialVersionUID = -7459874883460700642L;
    private static final String DEFAULT_MESSAGE = "이미 사용중인 이름입니다.";

    public MenuCategoryNameDuplicatedException() {
        super(DEFAULT_MESSAGE);
    }

    public MenuCategoryNameDuplicatedException(String message) {
        super(message);
    }
}
