package com.depromeet.team5.domain.store;

import lombok.Value;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Value(staticConstructor = "of")
public class StoreCreateValue {

    Double latitude;

    Double longitude;

    String storeName;
    /**
     * 가게 카테고리
     * v1 에서만 입력함
     */
    @Deprecated
    CategoryType categoryType;
    /**
     * 가게 카테고리 목록
     */
    List<CategoryType> categoryTypes;

    StoreType storeType;

    Set<DayOfWeek> appearanceDays;

    Set<PaymentMethodType> paymentMethods;

    List<MenuCreateValue> menus;
}
