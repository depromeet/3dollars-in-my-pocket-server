package com.depromeet.team5.domain.store;

import lombok.Value;

@Value(staticConstructor = "of")
public class ReviewCreateValue {
    String contents;
    Integer rating;
}
