package com.sparta.vroomvroom.domain.manager.model.dto.response;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerUserResponse {
    private Long userId;
    private String userName;
    private String phoneNumber;
    private String email;

    public ManagerUserResponse(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }
}
