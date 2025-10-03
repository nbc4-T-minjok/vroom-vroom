package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum BusinessStatus {
    SPECAIL_OPEN("특별영업"),
    TEMPORARILY_CLOSED("임시휴업"),
    OPEN("영업중"),
    CLOSED("영업종료");

    private final String status;

    BusinessStatus(String status) {
        this.status = status;
    }
}
