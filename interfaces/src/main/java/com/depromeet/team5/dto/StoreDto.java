package com.depromeet.team5.dto;

import com.depromeet.team5.domain.store.CategoryType;
import com.depromeet.team5.domain.store.PaymentMethodType;
import com.depromeet.team5.domain.store.StoreType;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Data
public class StoreDto {

    private Double latitude;

    private Double longitude;

    private String storeName;

    private StoreType storeType;

    private Set<DayOfWeek> appearanceDays;

    private Set<PaymentMethodType> paymentMethods;

    /**
     * menu 에서 카테고리 관리하게 변경되어서, category 직접 업데이트 하는 기능은 제거.
     */
    @Deprecated
    private CategoryType category;

    private List<MenuRequest> menu;
}
