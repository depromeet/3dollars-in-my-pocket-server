package com.depromeet.team5.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Location {
    Double latitude;
    Double longitude;
}
