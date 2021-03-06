package com.depromeet.team5.unit;

import com.depromeet.team5.domain.store.*;
import com.depromeet.team5.domain.user.SocialType;
import com.depromeet.team5.domain.user.User;
import com.depromeet.team5.exception.StoreNotFoundException;
import com.depromeet.team5.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class StoreJpaTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void save_츨몰시기와_결제방식의_중복_데이터는_제거되어_저장되어야함() {
        // given
        Set<DayOfWeek> appearanceDays = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, DayOfWeek.FRIDAY));
        Set<PaymentMethodType> paymentMethods = new HashSet<>(Arrays.asList(PaymentMethodType.CASH, PaymentMethodType.CASH, PaymentMethodType.ACCOUNT_TRANSFER));

        // when
        Store savedStore = createStore(appearanceDays, paymentMethods);

        // then
        Set<DayOfWeek> savedAppearanceDays = savedStore.getAppearanceDays().stream().map(AppearanceDay::getDay).collect(Collectors.toSet());
        Set<PaymentMethodType> savedPaymentMethods = savedStore.getPaymentMethods().stream().map(PaymentMethod::getMethod).collect(Collectors.toSet());
        assertThat(savedAppearanceDays).isEqualTo(Set.of(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        assertThat(savedPaymentMethods).isEqualTo(Set.of(PaymentMethodType.CASH, PaymentMethodType.ACCOUNT_TRANSFER));
    }

    @Test
    void update_출몰시기와_결제방식에_중복을_포함한_새로운_데이터를_추가하면_정상적으로_수정되어야함() {
        // given
        Set<DayOfWeek> appearanceDays = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        Set<PaymentMethodType> paymentMethods = new HashSet<>(Arrays.asList(PaymentMethodType.CASH, PaymentMethodType.ACCOUNT_TRANSFER));
        Store savedStore = createStore(appearanceDays, paymentMethods);

        Set<DayOfWeek> newAppearanceDays = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));
        Set<PaymentMethodType> newPaymentMethods = new HashSet<>(Arrays.asList(PaymentMethodType.CASH, PaymentMethodType.CARD));

        // when
        updateStore(savedStore, newAppearanceDays, newPaymentMethods);

        // then
        Store updatedStore = storeRepository.findById(savedStore.getId()).orElseThrow(() -> new StoreNotFoundException(savedStore.getId()));
        Set<DayOfWeek> updatedAppearanceDays = updatedStore.getAppearanceDays().stream().map(AppearanceDay::getDay).collect(Collectors.toSet());
        Set<PaymentMethodType> updatedPaymentMethods = updatedStore.getPaymentMethods().stream().map(PaymentMethod::getMethod).collect(Collectors.toSet());
        assertThat(updatedAppearanceDays).isEqualTo(Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY));
        assertThat(updatedPaymentMethods).isEqualTo(Set.of(PaymentMethodType.CASH, PaymentMethodType.CARD));
    }

    private Store createStore(Set<DayOfWeek> appearanceDays, Set<PaymentMethodType> paymentMethods) {
        User user = User.of("socialId", SocialType.GOOGLE);
        List<Image> images = Collections.emptyList();
        StoreCreateValue storeCreateValue = StoreCreateValue.of(
                37.0,
                127.0,
                "storeName",
                StoreType.ROAD,
                appearanceDays,
                paymentMethods,
                CategoryType.BUNGEOPPANG,
                Collections.emptyList()
        );
        Store store = Store.from(storeCreateValue, images, user);
        return storeRepository.save(store);
    }

    private Store updateStore(Store store, Set<DayOfWeek> appearanceDays, Set<PaymentMethodType> paymentMethods) {
        List<Image> images = Collections.emptyList();
        StoreUpdateValue storeUpdateValue = StoreUpdateValue.of(
                37.0,
                127.0,
                "storeName",
                StoreType.ROAD,
                appearanceDays,
                paymentMethods,
                Collections.emptyList()
        );
        store.setStore(storeUpdateValue, images);
        return store;
    }
}
