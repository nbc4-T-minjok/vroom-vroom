package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_CUSTOMER("CUSTOMER"),
    ROLE_OWNER("OWNER"),
    ROLE_MANAGER("MANAGER"),
    ROLE_MASTER("MASTER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
