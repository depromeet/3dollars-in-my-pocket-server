package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.Image;
import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private String url;

    public static ImageDto from(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.id = image.getId();
        imageDto.url = image.getUrl();
        return imageDto;
    }
}
