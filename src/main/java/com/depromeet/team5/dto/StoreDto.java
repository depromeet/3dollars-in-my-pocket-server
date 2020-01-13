package com.depromeet.team5.dto;

import com.depromeet.team5.domain.CategoryTypes;
import com.depromeet.team5.domain.Menu;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class StoreDto {
    private Double latitude;

    private Double longitude;

    private String storeName;

    private CategoryTypes category;

    private List<MultipartFile> image;

    private List<Menu> menu;
}
