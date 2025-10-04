package com.sparta.vroomvroom.domain.user.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
public class UserSignupRequest {

    private String userName;

    private String password;

    private String nickName;

    //Todo: Enum 생성
    private String type;

    private String name;

    //Todo: birthDay로 변경 제안
    private LocalDate birthDate;

    private String gender;

    private String phoneNumber;

    private String email;


}
