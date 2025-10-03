package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("주문요청"),
    CANCELED("주문취소"),
    ACCEPTED("주문수락"),
    COOKING("조리중"),
    COOKING_COMPLETED("조리완료"),
    PICKED_UP("픽업완료"),
    DELIVERING("배달중"),
    DELIVERY_COMPLETED("배달완료"),
    COMPLETED("주문완료");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
