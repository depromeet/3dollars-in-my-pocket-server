package com.depromeet.team5.paprika.input;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaprikaStoreItemProcessorTest {
    private final PaprikaStoreItemProcessor sut = new PaprikaStoreItemProcessor();

    @Test
    void parseLatitude() {
        String locationString = "{\"latitude\":37.2662116 \"longitude\":126.9606571}";
        Double actual = sut.parseLatitude(locationString);
        assertThat(actual).isEqualTo(37.2662116);
    }

    @Test
    void parseLongitude() {
        String locationString = "{\"latitude\":37.2662116 \"longitude\":126.9606571}";
        Double actual = sut.parseLongitude(locationString);
        assertThat(actual).isEqualTo(126.9606571);
    }

    @Test
    void parseCreatedAt() {
        String createdAtString = "Wed Nov 25 2020 17:24:06 GMT+0900 (KST)";
        LocalDateTime actual = sut.parseCreatedAt(createdAtString);
        assertThat(actual).isEqualTo("2020-11-25T17:24:06");
    }
}