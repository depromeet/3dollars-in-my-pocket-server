package com.depromeet.team5.domain.store;

import lombok.Value;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Value(staticConstructor = "of")
public class StoreUpdateValue {

    Double latitude;

    Double longitude;

    String storeName;
    /**
     * 가게 카테고리
     * v1 에서만 사용하는 값
     */
    @Deprecated
    CategoryType categoryType;
    /**
     * 카테고리 목록
     */
    List<CategoryType> categoryTypes;

    StoreType storeType;

    Set<DayOfWeek> appearanceDays;

    Set<PaymentMethodType> paymentMethods;

    List<MenuCreateValue> menus;
}
