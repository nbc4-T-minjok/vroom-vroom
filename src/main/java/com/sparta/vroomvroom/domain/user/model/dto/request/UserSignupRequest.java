package com.sparta.vroomvroom.domain.user.model.dto.request;

import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.conmon.constants.UserType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class UserSignupRequest {

    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,10}$",
            message = "영문과 숫자로 4~10자만 가능합니다.")
    private String userName;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
            message = "영문 대소문자, 숫자, 특수문자를 포함해 8~15자여야 합니다.")
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣]{5,20}$",
            message = "영문/한글/숫자로 5~20자만 가능합니다.")
    private String nickName;

    //Todo: Enum 생성
    @NotBlank
    @Pattern(
            regexp = "^[A-Z]{1,20}$",
            message = "영문 대문자로 최대 20자까지 가능합니다.")
    private UserType type;

    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z가-힣]{1,20}$",
            message = "영문과 한글만, 최대 20자까지 가능합니다.")
    private String name;

    //Todo: birthDay로 변경 제안
    @NotBlank
    @Past(message = "생일은 오늘 이전 날짜여야 합니다.")
    private LocalDate birthDate;

    @NotBlank
    @Pattern(
            regexp = "^[가-힣]+$",
            message = "한글만 입력 가능합니다.")
    private String gender;

    @NotBlank
    @Pattern(
            regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "휴대폰 번호는 000-0000-0000 형식만 가능합니다.")
    private String phoneNumber;

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 50, message = "이메일은 최대 50자까지 가능합니다.")
    private String email;

    //todo: 명세서에 role 받는거 추가하기
    @NotBlank
    @Size(max = 20, message = "최대 20자까지 입력 가능합니다.")
    private UserRole role;


}
