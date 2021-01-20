package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class StoreDto {

    private Double latitude;

    private Double longitude;

    private String storeName;

    /**
     * menu 에서 카테고리 관리하게 변경되어서, category 직접 업데이트 하는 기능은 제거.
     */
    @Deprecated
    private CategoryType category;

    private List<MultipartFile> image;

    private List<MenuRequest> menu;
}
