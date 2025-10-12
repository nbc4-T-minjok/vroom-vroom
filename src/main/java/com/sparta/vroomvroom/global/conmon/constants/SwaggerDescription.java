package com.sparta.vroomvroom.global.conmon.constants;

public class SwaggerDescription {
    // 배송지 등록 설명
    public static final String ADDRESS_CREATE_REQUEST =
            "회원가입 진행 후 배송지 등록 요청을 해주세요.\n" +
            "요청 가능 권한 : customer / manager";

    // 배송지 수정 설명
    public static final String ADDRESS_UPDATE_REQUEST =
            "먼저 배송지 등록 후 조회 요청을 해주세요.\n" +
            "배송지 조회 후 반환된 addreddId 값이 필요합니다.\n" +
            "요청 가능 권한 : customer / manager";

    // 영업시간 등록 설명
    public static final String BUSINESS_HOUR_CREATE_REQUEST =
            "회원가입을 진행 후 영업시간 등록 요청을 해주세요.\n" +
            "요청 가능 권한 : owner / manager";

    // 영업시간 수정 설명
    public static final String BUSINESS_HOUR_UPDATE_REQUEST =
            "먼저 영업시간 등록 후 조회 요청을 해주세요.\n" +
            "영업시간 조회 후 반환된 businessHourId 값이 필요합니다.\n" +
            "요청 가능 권한 : customer / manager";
}
