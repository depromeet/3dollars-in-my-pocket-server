package com.depromeet.team5.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageRequestDto {
    private Long storeId;
    private List<MultipartFile> image;
}
