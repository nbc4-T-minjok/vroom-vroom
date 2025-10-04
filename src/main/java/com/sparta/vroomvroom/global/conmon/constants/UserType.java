package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum UserType {
    INAPP("INAPP"),
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
    GITHUB("GITHUB"),;

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
