package com.depromeet.team5.paprika.input;

import lombok.Data;

/**
 * csv 파일 읽을 때 사용하는 모델
 */
@Data
public class PaprikaStoreItem {
    /**
     * 캐다 userId
     */
    private String userId;
    /**
     * 캐다 이미지 파일 이름
     */
    private String fileName;
    /**
     * 캐다 위경도
     */
    private String location;
    /**
     * 캐다 등록시각
     */
    private String createdAt;
}
