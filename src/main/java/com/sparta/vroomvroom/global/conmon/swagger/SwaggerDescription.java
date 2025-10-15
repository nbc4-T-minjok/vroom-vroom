package com.sparta.vroomvroom.global.conmon.swagger;

public class SwaggerDescription {
    // 배송지 등록 설명
    public static final String ADDRESS_CREATE_REQUEST =
            "회원가입과 로그인 진행 후 배송지 등록 요청을 해주세요.<br>" +
            "요청 가능 권한 : customer / manager";

    // 배송지 수정 설명
    public static final String ADDRESS_UPDATE_REQUEST =
            "먼저 배송지 등록 후 조회 요청을 해주세요.<br>" +
            "배송지 조회 후 반환된 addreddId 값이 필요합니다.<br>" +
            "요청 가능 권한 : customer / manager";

    // 영업시간 등록 설명
    public static final String BUSINESS_HOUR_CREATE_REQUEST =
            "회원가입과 로그인, 업체 등록 진행 후 영업시간 등록 요청을 해주세요.<br>" +
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

    //회원 가입 설명
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

    // 장바구니 메뉴 추가 설명
    public static final String CART_ADD_REQUEST =
            "회원가입과 로그인 진행 후 장바구니에 메뉴를 추가합니다.<br>" +
                    "요청 가능 권한 : customer";

    // 장바구니 메뉴 수량 변경 설명
    public static final String CART_UPDATE_REQUEST =
            "장바구니에 담긴 특정 메뉴의 수량/옵션을 수정합니다.<br>" +
                    "장바구니 조회 후 반환된 cartMenuId 값이 필요합니다.<br>" +
                    "요청 가능 권한 : customer";

    //매니저 등록 설명
    public static final String MANAGER_REGISTER_REQUEST = """
            userName, email, phoneNumber는 중복이 불가능 합니다.
            비밀번호는 대소문자,특수문자,숫자 모두를 포함해서 8~15자로 설정해주세요.
            """;
}
