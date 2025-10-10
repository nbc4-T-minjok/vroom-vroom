package com.sparta.vroomvroom.domain.user.model.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserUpdatedRequest {
    @Pattern(
            regexp = "^[a-zA-Z0-9가-힣]{5,20}$",
            message = "닉네임은 영문/한글/숫자로 5~20자만 가능합니다.")
    private String nickName;

    @Pattern(
            regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "휴대폰 번호는 000-0000-0000 형식만 가능합니다.")
    private String phoneNumber;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 50, message = "이메일은 최대 50자까지 가능합니다.")
    private String email;

    //정보 수정시 최소 1개 이상의 수정항목이 전달됐는지 판단
    public boolean isEmpty() {
        return (nickName == null || nickName.isBlank())
                && (phoneNumber == null || phoneNumber.isBlank())
                && (email == null || email.isBlank());
    }

}