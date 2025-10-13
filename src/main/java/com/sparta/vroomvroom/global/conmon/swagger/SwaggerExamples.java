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
}
