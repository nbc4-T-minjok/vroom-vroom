package com.sparta.vroomvroom.global.conmon.constants;

public class SwaggerExamples {
    // 배송지 등록 예시
    public static final String ADDRESS_CREATE_REQUEST = """
            {
              "addressName": "우리집1",
              "address": "경기도 어쩌고",
              "detailAddress": "00아파트 00동 00호",
              "zipCode": "12345",
              "isDefault": true,
              "location" : { "lat": 37.123456, "lng": 127.123456 }
            }
            """;

    // 배송지 수정 예시
    public static final String ADDRESS_UPDATE_REQUEST = """
            {
              "addressName": "우리집1 수정",
              "address": "경기도 어쩌고 저쩌고",
              "detailAddress": "ㅁㅁ아파트 ㅁㅁ동 ㅁㅁ호",
              "zipCode": "12340",
              "isDefault": true,
              "location" : { "lat": 38.123456, "lng": 128.123456 }
            }
            """;

    // 영업시간 등록 예시
    public static final String BUSINESS_HOUR_CREATE_REQUEST = """
            {
              "day": "MON",
              "openedAt": "10:00",
              "closedAt": "22:00"
            }
            """;

    // 영업시간 수정 예시
    public static final String BUSINESS_HOUR_UPDATE_REQUEST = """
            {
              "closedAt": "23:00"
            }
            """;
}
