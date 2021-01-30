package com.depromeet.team5.dto;


import com.depromeet.team5.domain.store.Image;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageResponse {

    private Long imageId;

    private String url;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ImageResponse of(Image image) {
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.imageId = image.getId();
        imageResponse.url = image.getUrl();
        imageResponse.createdAt = image.getCreatedAt();
        imageResponse.updatedAt = image.getUpdatedAt();
        return imageResponse;
    }
}