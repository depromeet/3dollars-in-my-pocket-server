package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Data
public class StoreDto {

    private Double latitude;

    private Double longitude;

    private String storeName;
    /**
     * 대표 카테고리
     * v1 에서만 입력하는 값
     */
    @Deprecated
    private CategoryType category;
    /**
     * 가게 카테고리 목록
     * v1 에서 입력하지 않는 값
     */
    @Nullable
    private List<CategoryType> categories;

    @Nullable
    private StoreType storeType;

    @Nullable
    private Set<DayOfWeek> appearanceDays;

    @Nullable
    private Set<PaymentMethodType> paymentMethods;

    private List<MultipartFile> image;

    private List<MenuRequest> menu;
}
