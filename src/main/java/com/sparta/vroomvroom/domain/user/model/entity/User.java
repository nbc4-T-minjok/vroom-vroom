package com.sparta.vroomvroom.domain.user.model.entity;

import com.sparta.vroomvroom.domain.manager.model.dto.request.ManagerRegisterRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserUpdatedRequest;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.conmon.constants.UserType;
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
    @Enumerated(EnumType.STRING)
    private UserType type;

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


    public User(UserSignupRequest req, String encodedPassword) {
        if(req.getRole() == UserRole.ROLE_MANAGER || req.getRole() != UserRole.ROLE_MASTER){
            throw new IllegalArgumentException("설정할 수 없는 권한입니다. CUSTOMER, OWNER 둘 중 한 가지 권한으로만 가입할 수 있습니다..");
        }
        this.userName = req.getUserName();
        this.password = encodedPassword;
        this.type = req.getType();
        this.nickName = req.getNickName();
        this.name = req.getName();
        this.birthDate = req.getBirthDate();
        this.gender = req.getGender();
        this.phoneNumber = req.getPhoneNumber();
        this.email = req.getEmail();
        this.role = req.getRole();

    }

    public User(ManagerRegisterRequest req, String encodedPassword) {
        this.userName = req.getUserName();
        this.password = encodedPassword;
        this.type = req.getType();
        this.nickName = req.getNickName();
        this.name = req.getName();
        this.birthDate = req.getBirthDate();
        this.gender = req.getGender();
        this.phoneNumber = req.getPhoneNumber();
        this.email = req.getEmail();
        this.role = UserRole.ROLE_MANAGER;
    }
}
