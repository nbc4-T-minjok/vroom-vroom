package com.sparta.vroomvroom.global.conmon.swagger;

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

    // 리뷰 작성_주문 예시
    public static final String REVIEW_ORDER_CREATE_REQUEST = """
            {
                "review": {
                        "rate" : 5,
                        "contents : "음식이 맛있고 배달이 빨라요"
                },
                "images": "(binary)"
            }
            """;

    // 리뷰 작성_업체 예시
    public static final String REVIEW_COMPANY_CREATE_REQUEST = """
            {
                "reviewId" : "fcb445cd-d989-4897-a267-6df77c4b1d1",
                "contents" : "주문해주셔서 감사합니다."
            }
            """;

    // 리뷰 수정_고객 예시
    public static final String REVIEW_ORDER_UPDATE_REQUEST = """
            {
                "review": {
                    "contents": "수정하는 리뷰입니다."
                },
                "images": "(binary)"
            }
            """;

    // 리뷰 수정_업체 예시
    public static final String REVIEW_COMPANY_UPDATE_REQUEST = """
            {
                "contents" : "사장님리뷰 수정입니다. 감사합니다."
            }
            """;
}
