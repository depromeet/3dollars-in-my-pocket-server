package com.depromeet.team5.domain.store;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class StoresByCategoryAndDistance {
    List<Store> storesIn50;
    List<Store> storesIn100;
    List<Store> storesIn500;
    List<Store> storesIn1000;
}
