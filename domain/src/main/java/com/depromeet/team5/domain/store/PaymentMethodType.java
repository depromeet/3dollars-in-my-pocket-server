package com.depromeet.team5.domain.store;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethodType {
    CASH("현금"),
    ACCOUNT_TRANSFER("계좌이체"),
    CARD("카드");

    private final String paymentMethodType;
}
