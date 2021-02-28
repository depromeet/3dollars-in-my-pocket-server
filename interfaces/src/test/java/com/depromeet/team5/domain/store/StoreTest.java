package com.depromeet.team5.domain.store;

import com.depromeet.team5.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class StoreTest {
    @Test
    void 가게_생성시_rating_은_0_이어야함() {
        StoreCreateValue storeCreateValue = StoreCreateValue.of(
                37.0,
                127.0,
                "storeName",
                null,
                Collections.emptyList(),
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptyList()
        );
        Store store = Store.from(storeCreateValue, Collections.emptyList(), new User());
        assertThat(store.getRating()).isZero();
    }

    @Test
    void 가게_생성시_category_는_null_이_아니어야함__v2_미만() {
        // App version 1.x
        // category is not null,
        // categories is empty
        StoreCreateValue storeCreateValue = StoreCreateValue.of(
                37.0,
                127.0,
                "storeName",
                CategoryType.HOTTEOK,
                Collections.emptyList(),
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptyList()
        );
        Store store = Store.from(storeCreateValue, Collections.emptyList(), new User());
        assertThat(store.getCategory()).isEqualTo(CategoryType.HOTTEOK);
    }

    @Test
    void 가게_생성시_category_는_null_이_아니어야함__v2_이상() {
        // App version >= 2.0
        // category is null,
        // categories is not empty
        StoreCreateValue storeCreateValue = StoreCreateValue.of(
                37.0,
                127.0,
                "storeName",
                null,
                Arrays.asList(CategoryType.GYERANPPANG, CategoryType.BUNGEOPPANG),
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptyList()
        );
        Store store = Store.from(storeCreateValue, Collections.emptyList(), new User());
        // categories 리스트 중 첫번째 값을 사용함
        assertThat(store.getCategory()).isEqualTo(CategoryType.GYERANPPANG);
    }
}
