package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum MenuStatus {
    AVAILABLE("판매중"),
    SOLD_OUT("품절"),
    TEMPORARILY_SOLD_OUT("일시 품절");

    private final String status;

    MenuStatus(String status) {
        this.status = status;
    }
}
