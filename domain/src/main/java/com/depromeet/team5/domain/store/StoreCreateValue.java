package com.depromeet.team5.domain.store;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class StoreCreateValue {
    Double latitude;

    Double longitude;

    String storeName;

    CategoryType category;

    List<MenuCreateValue> menus;
}
