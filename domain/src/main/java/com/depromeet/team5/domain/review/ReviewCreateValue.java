package com.depromeet.team5.domain.review;

import lombok.Value;

@Value(staticConstructor = "of")
public class ReviewCreateValue {
    String content;
    Integer rating;
}
