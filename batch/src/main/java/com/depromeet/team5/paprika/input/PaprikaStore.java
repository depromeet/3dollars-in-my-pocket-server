package com.depromeet.team5.paprika.input;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class PaprikaStore {
    /**
     * 캐다 앱에서 등록한 사용자 Id
     */
    private String userId;
    /**
     * 가게 이름 (캐다 앱에서 등록할 땐 정보 없음)
     */
    private String storeName;
    /**
     * 캐다 앱에서 등록한 위도
     */
    private Double latitude;
    /**
     * 캐다 앱에서 등록한 경도
     */
    private Double longitude;
    /**
     * 캐다 앱에서 등록한 이미지 파일 이름
     */
    private String imageUrl;
    /**
     * 캐다 앱에서 등록한 시각
     */
    private LocalDateTime createdAt;
}
