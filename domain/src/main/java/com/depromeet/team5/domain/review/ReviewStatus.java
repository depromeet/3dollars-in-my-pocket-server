package com.depromeet.team5.domain.review;

public enum ReviewStatus {
    POSTED(true),
    FILTERED(false),
    DELETED(false),
    ;

    private final boolean readable;

    ReviewStatus(boolean readable) {
        this.readable = readable;
    }
}
