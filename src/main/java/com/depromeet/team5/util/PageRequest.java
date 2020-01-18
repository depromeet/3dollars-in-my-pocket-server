package com.depromeet.team5.util;

import org.springframework.data.domain.Sort;

public final class PageRequest {

    private int page;
    private int size;

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 5;
        int MAX_SIZE = 10;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(
                page -1, size, Sort.Direction.DESC, "createdAt"
        );
    }
}
