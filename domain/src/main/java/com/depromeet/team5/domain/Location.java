package com.depromeet.team5.domain;

import lombok.Value;

@Value
public class Location {
    private static final Location EMPTY = Location.of(null, null);

    Double latitude;
    Double longitude;

    public static Location of(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return EMPTY;
        }
        return new Location(latitude, longitude);
    }

    public boolean isEmpty() {
        return latitude == null || longitude == null;
    }
}
