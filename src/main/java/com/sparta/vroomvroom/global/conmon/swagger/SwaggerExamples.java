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
             }""";
    // 특별영업시간 등록 예시
    public static final String SPECIAL_BUSINESS_HOUR_CREATE_REQUEST = """
            {
            	"date" : "2025-12-29",
            	"openedAt" : "08:00:00",
            	"openedAt" : "22:00:00",
            	"businessStatus" : "OPEN"
            }
            """;

    // 특별영업시간 수정 예시
    public static final String SPECIAL_BUSINESS_HOUR_UPDATE_REQUEST = """
            {
            	"date" : "2025-12-29",
            	"openedAt" : null,
            	"openedAt" : null,
            	"businessStatus" : "CLOSED"
            }
            """;
    // 업체 카테고리 등록 예시
    public static final String COMPANY_CATEGORY_CREATE_REQUEST = """
            {
                "companyCategoryName" : "한식"
            }
            """;

    public static final String COMPANY_CATEGORY_UPDATE_REQUEST = """
            {
                "companyCategoryName" : "한식"
            }
            """;

    // 주문 생성 예시
    public static final String ORDER_CREATE_REQUEST = """
            {
              "userAddressId": "00000000-0000-0000-0000-300000000001",
              "orderRequest": "문 앞에 놔주세요",
              "paymentMethod": "CARD"
            }
            """;
    public static final String ORDER_STATUS_UPDATE_REQUEST = """
            {
              "orderStatus": "ACCEPTED"
            }
            """;

    //회원가입 예시
    public static final String USER_SIGNUP_REQUEST = """
            {
              "userName": "testuser0",
              "password": "Abc123!@#",
              "nickName": "닉네임홍길동",
              "type": "INAPP",
              "name": "홍길순",
              "birthDate": "1990-01-01",
              "gender": "남",
              "phoneNumber": "010-0123-1234",
              "email": "testuser0@example.com",
              "role": "ROLE_CUSTOMER"
            }
            """;

    //회원 정보 수정 예시
    public static final String USER_DETAIL_UPDATE_REQUEST = """
            {
              "nickName": "변경된홍길동",
              "phoneNumber": "010-0123-1321",
              "email": "testuser0chged@example.com"
            }
            """;

    //비밀번호 변경 예시
    public static final String USER_PASSWORD_CHANGE_REQUEST = """
              "currentPassword": "Abc123!@#",
               "newPassword": "Abc321!@#"
            """;

    public static final String CART_ADD_REQUEST = """
            {
                "menuId": "00000000-0000-0000-0000-200000001001",
                "menuAmount": 2
            }
            """;

                public static final String CART_UPDATE_REQUEST = """
            {
              "menuAmount": 7
            }
            """;

    public static final String COMPANY_REGISTER_REQUEST = """
            {
            	"companyName": "업체명",
            	"companyDescription" : "업체설명",
            	"phoneNumber" : "010-0000-0000",
            	"deliveryFee" : 3000,
            	"deliveryRadius" : 2000,
            	"ownerName" : "사장이름",
            	"bizRegNo" : "123-45-13243",
            	"address" : "주소",
            	"detailAddress" : "상세주소",
            	"zipCode" : "00000",
            	"location" : { "lat": 37.123456, "lng": 127.123456 }
            }
            """;
}
