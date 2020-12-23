package com.depromeet.team5.domain.review;

public enum ReviewStatus {
    POSTED(true),
    FILTERED(false),
    DELETED(false),
    ;

    private final boolean visible;

    ReviewStatus(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
