package com.depromeet.team5.domain.store;

import lombok.Value;

@Value(staticConstructor = "of")
public class MenuCreateValue {
    String name;
    String price;
}
