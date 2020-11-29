package com.depromeet.team5.domain.store;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class StoreUpdateValue {
    Double latitude;
    Double longitude;
    String storeName;
    List<MenuCreateValue> menus;
}
