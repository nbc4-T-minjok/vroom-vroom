package com.sparta.vroomvroom.global.conmon.swagger;

public class SwaggerDescription {
    // 배송지 등록 설명
    public static final String ADDRESS_CREATE_REQUEST =
            "회원가입 진행 후 배송지 등록 요청을 해주세요.<br>" +
            "요청 가능 권한 : customer / manager";

    // 배송지 수정 설명
    public static final String ADDRESS_UPDATE_REQUEST =
            "먼저 배송지 등록 후 조회 요청을 해주세요.<br>" +
            "배송지 조회 후 반환된 addreddId 값이 필요합니다.<br>" +
            "요청 가능 권한 : customer / manager";

    // 영업시간 등록 설명
    public static final String BUSINESS_HOUR_CREATE_REQUEST =
            "먼저 회원가입과 업체 등록 진행 후 영업시간 등록 요청을 해주세요.<br>" +
            "업체 조회 후 반환된 companyId 값이 필요합니다.<br>" +
            "요청 가능 권한 : owner / manager";

    // 영업시간 수정 설명
    public static final String BUSINESS_HOUR_UPDATE_REQUEST =
            "먼저 영업시간 등록 후 조회 요청을 해주세요.<br>" +
            "영업시간 조회 후 반환된 businessHourId 값과 companyId 값이 필요합니다.<br>" +
            "요청 가능 권한 : customer / manager";

    // 특별영업시간 등록 설명
    public static final String SPECIAL_BUSINESS_HOUR_CREATE_REQUEST =
            "먼저 회원가입과 업체 등록 진행 후 요청을 해주세요.<br>" +
                    "업체 조회 후 반환된 companyId 값이 필요합니다.<br>" +
                    "요청 가능 권한 : owner / manager";

    // 특별영업시간 수정 및 삭제 설명
    public static final String SPECIAL_BUSINESS_HOUR_UPDATE_REQUEST =
            "먼저 회원가입과 업체 등록 진행 후 요청을 해주세요.<br>" +
                    "특별영업시간 조회 후 반환된 companyId 값과 specialBusinessHourId 값이 필요합니다.<br>" +
                    "요청 가능 권한 : owner / manager";;
    // 업체 카테고리 등록 설명
    public static final String COMPANY_CATEGORY_CREATE_REQUEST =
            "관리자 권한만 요청을 해주세요.<br>" +
                    "요청 가능 권한 : manager / master";

    // 업체 카테고리 수정 및 삭제 설명
    public static final String COMPANY_CATEGORY_UPDATE_REQUEST =
            "관리자 권한만 요청을 해주세요.<br>" +
                    "업체 카테고리 조회 후 반환된 companyCategoryId 값이 필요합니다.<br>"  +
                    "요청 가능 권한 : manager / master";
    // 주문 생성 설명
    public static final String ORDER_CREATE_REQUEST =
            "회원가입과 로그인 진행 후 주문 생성 요청을 해주세요.<br>" +
            "companyId 값과 userAddressId 값이 필요합니다.<br>" +
            "요청 가능 권한 : customer";
    public static final String USER_SIGNUP_REQUEST = """
            userName, email, phoneNumber는 중복이 불가능 합니다.
            비밀번호는 대소문자,특수문자,숫자 모두를 포함해서 8~15자로 설정해주세요.
            """;

    //회원 정보 수정 예시
    public static final String USER_DETAIL_UPDATE_REQUEST = """
            userName, email, phoneNumber는 중복이 불가능 합니다.
            변경하고 싶은 항목만 선택적으로 변경할 수 있습니다.
            """;

    //비밀번호 변경 예시
    public static final String USER_PASSWORD_CHANGE_REQUEST = """
            비밀번호는 대소문자,특수문자,숫자 모두를 포함해서 8~15자로 설정해주세요.
            """;

    // 메뉴 등록 설명
    public static final String MENU_CREATE_REQUEST =
            """
            업체 등록 후 해당 업체의 메뉴를 생성합니다.
            요청 가능 권한: owner / manager
            - menuStatus: 메뉴 상태 (AVAILABLE, SOLD_OUT, TEMPORARILY_SOLD_OUT)
            - aiDescription: true 시 AI가 메뉴 설명을 자동 생성합니다.
            """;

    // 메뉴 수정 설명
    public static final String MENU_UPDATE_REQUEST =
            """
            등록된 메뉴의 정보를 수정합니다.
            요청 가능 권한: owner / manager
            필요한 필드만 포함하면 해당 값만 수정됩니다.
            - menuStatus: 메뉴 상태 (AVAILABLE, SOLD_OUT, TEMPORARILY_SOLD_OUT)
            """;

}
