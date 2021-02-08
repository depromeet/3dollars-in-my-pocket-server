package com.depromeet.team5.dto;


import com.depromeet.team5.domain.store.Image;
import lombok.Data;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Data
public class ImageResponse {
    private Long id;

    private String url;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ImageResponse from(Image image) {
        Assert.notNull(image, "'image' must not be null");

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.id = image.getId();
        imageResponse.url = image.getUrl();
        imageResponse.createdAt = image.getCreatedAt();
        imageResponse.updatedAt = image.getUpdatedAt();
        return imageResponse;
    }
}