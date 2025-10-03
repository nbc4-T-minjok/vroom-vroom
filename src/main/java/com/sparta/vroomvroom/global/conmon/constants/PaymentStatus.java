package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAID("결제완료"),
    PENDING("결제대기"),
    FAILED("결제실패"),
    REFUNDED("환불");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
