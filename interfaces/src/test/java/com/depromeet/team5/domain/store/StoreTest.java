package com.depromeet.team5.domain.store;

import com.depromeet.team5.domain.user.User;
import org.junit.jupiter.api.Test;

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
}
