package com.depromeet.team5.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class StoreUpdateDto {

    private Double latitude;

    private Double longitude;

    private String storeName;

    private List<MenuDto> menu;
}
