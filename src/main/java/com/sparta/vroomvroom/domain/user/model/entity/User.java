package com.sparta.vroomvroom.domain.user.model.entity;

import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @Column(name = "nick_name",nullable = false, length = 20)
    private String nickName;

    @Column(name = "name",nullable = false, length = 20)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender",nullable = false, length = 10)
    private String gender;

    @Column(name = "phone_number",nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email",nullable = false, length = 50)
    private String email;

    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;


}
