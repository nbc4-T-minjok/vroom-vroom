package com.sparta.vroomvroom.domain.manager.model.dto.request;

import com.sparta.vroomvroom.global.conmon.constants.UserType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ManagerRegisterRequest {
    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9]{4,10}$",
            message = "아이디는 영문과 숫자로 4~10자만 가능합니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해 8~15자여야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣]{5,20}$",
            message = "닉네임은 영문/한글/숫자로 5~20자만 가능합니다.")
    private String nickName;

    @NotNull(message = "회원 타입은 필수 입력 항목입니다.")
    private UserType type;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Pattern(
            regexp = "^[a-zA-Z가-힣]{1,20}$",
            message = "이름은 영문과 한글만, 최대 20자까지 가능합니다.")
    private String name;

    @NotNull(message = "생일은 필수 입력 항목입니다.")
    @Past(message = "생일은 오늘 이전 날짜여야 합니다.")
    private LocalDate birthDate;

    @NotBlank(message = "성별은 공백일 수 없습니다.")
    @Pattern(
            regexp = "^[가-힣]+$",
            message = "성별은 한글만 입력 가능합니다.")
    private String gender;

    @NotBlank(message = "휴대폰 번호는 공백일 수 없습니다.")
    @Pattern(
            regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "휴대폰 번호는 000-0000-0000 형식만 가능합니다.")
    private String phoneNumber;

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 50, message = "이메일은 최대 50자까지 가능합니다.")
    private String email;
}
