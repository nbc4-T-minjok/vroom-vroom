package com.sparta.vroomvroom.global.conmon.constants;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_OWNER("ROLE_OWNER"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_MASTER("ROLE_MASTER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
