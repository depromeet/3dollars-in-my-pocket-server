package com.depromeet.team5.paprika.input;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaprikaStoreItemProcessor implements ItemProcessor<PaprikaStoreItem, PaprikaStore> {
    private static final Pattern PATTERN_LATITUDE = Pattern.compile(".*\"latitude\":(\\d+\\.\\d+).*");
    private static final Pattern PATTERN_LONGITUDE = Pattern.compile(".*\"longitude\":(\\d+\\.\\d+).*");
    private static final DateTimeFormatter CREATED_AT_FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss", Locale.US);
    private static final String IMAGE_URL_PREFIX = "https://three-dollars-in-my-pocket-dev.s3.ap-northeast-2.amazonaws.com/paprika/";

    /**
     * csv 파일의 데이터를 우리 DB에 입력 가능한 형태로 가공한다.
     *
     * @param item csv 파일 한 줄의 데이터
     * @return paprikaStore 정보
     */
    @Override
    public PaprikaStore process(PaprikaStoreItem item) {
        return PaprikaStore.builder()
                .userId(item.getUserId())
                .storeName("파프리카")
                .latitude(parseLatitude(item.getLocation()))
                .longitude(parseLongitude(item.getLocation()))
                .imageUrl(IMAGE_URL_PREFIX + item.getFileName())
                .createdAt(parseCreatedAt(item.getCreatedAt()))
                .build();
    }

    /**
     * 주어진 위치 정보에서 위도 값을 파싱한다.
     *
     * @param locationString csv 에 저장된 위치 정보
     * @return 위도
     */
    Double parseLatitude(String locationString) {
        Matcher matcher = PATTERN_LATITUDE.matcher(locationString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse latitude. input: " + locationString);
        }
        return Double.valueOf(matcher.group(1));
    }

    /**
     * 주어진 위치 정보에서 경도 값을 파싱한다.
     *
     * @param locationString csv 에 저장된 위치 정보
     * @return 경도
     */
    Double parseLongitude(String locationString) {
        Matcher matcher = PATTERN_LONGITUDE.matcher(locationString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse longitude. input: " + locationString);
        }
        return Double.valueOf(matcher.group(1));
    }

    /**
     * 주어진 시간 정보를 파싱한다.
     *
     * @param source 문자열로 된 시간 정보
     * @return 타임존 정보를 제외하고 파싱한 시간 값
     */
    LocalDateTime parseCreatedAt(String source) {
        return LocalDateTime.from(CREATED_AT_FORMATTER.parse(source.substring(0, source.indexOf('G') - 1)));
    }

}
