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

    StoreType storeType;

    Set<DayOfWeek> appearanceDays;

    Set<PaymentMethodType> paymentMethods;

    CategoryType category;

    List<MenuCreateValue> menus;
}
