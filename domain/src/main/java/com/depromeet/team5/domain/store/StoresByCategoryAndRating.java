package com.depromeet.team5.domain.store;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class StoresByCategoryAndRating {
    List<Store> storesRatingOver4;
    List<Store> storesRatingOver3;
    List<Store> storesRatingOver2;
    List<Store> storesRatingOver1;
    List<Store> storesRatingOver0;
}
