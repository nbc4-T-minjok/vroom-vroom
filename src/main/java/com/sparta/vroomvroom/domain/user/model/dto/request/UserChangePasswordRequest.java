package com.sparta.vroomvroom.domain.user.model.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해 8~15자여야 합니다.")
    private String currentPassword;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{8,15}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함해 8~15자여야 합니다.")
    private String newPassword;

}
