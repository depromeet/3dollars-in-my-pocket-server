package com.depromeet.team5.dto;

import com.depromeet.team5.domain.Image;
import lombok.Data;

@Data
public class ImageDto {

    private String url;

    public static ImageDto from(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.url = image.getUrl();
        return imageDto;
    }
}
