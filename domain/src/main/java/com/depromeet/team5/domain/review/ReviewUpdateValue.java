package com.depromeet.team5.domain.review;

import lombok.Value;

@Value(staticConstructor = "of")
public class ReviewUpdateValue {
    String content;
    Integer rating;
}
