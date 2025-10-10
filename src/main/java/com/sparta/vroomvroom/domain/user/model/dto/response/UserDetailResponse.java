package com.sparta.vroomvroom.domain.user.model.dto.response;


import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.constants.UserType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDetailResponse {
    private String userName;
    private String nickName;
    private UserType type;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String phoneNumber;
    private String email;

    public UserDetailResponse(User user) {
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.type = user.getType();
        this.name = user.getName();
        this.birthDate = user.getBirthDate();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }
}
