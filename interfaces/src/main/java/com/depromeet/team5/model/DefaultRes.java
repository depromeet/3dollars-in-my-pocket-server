package com.depromeet.team5.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DefaultRes<T> {
    private String message;

    private T data;

    public DefaultRes(final String message) {
        this.message = message;
        this.data = null;
    }

    public static<T> DefaultRes<T> res(final String message) {
        return res(message, null);
    }

    public static<T> DefaultRes<T> res(final String message, final T t) {
        return DefaultRes.<T>builder()
                .data(t)
                .message(message)
                .build();
    }
}
