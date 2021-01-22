package com.depromeet.team5.domain.store;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class StoreCreateValue {

    Double latitude;

    Double longitude;

    String storeName;

    StoreType storeType;

    List<AppearanceDayType> appearanceDays;

    List<PaymentMethodType> paymentMethods;

    CategoryTypes category;

    List<MenuCreateValue> menus;
}
