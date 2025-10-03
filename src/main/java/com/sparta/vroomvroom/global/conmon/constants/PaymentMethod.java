package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CARD("카드"),
    CASH("현금"),
    KAKAO_PAY("카카오 페이");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }
}
