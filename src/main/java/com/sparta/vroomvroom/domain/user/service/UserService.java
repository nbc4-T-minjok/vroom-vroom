package com.sparta.vroomvroom.domain.user.service;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //아이디, 전화번호, 이메일 셋 중 하나라도 기존 회원의 값과 중복되면 안됨
    //중복 체크 후 DB insert
    public void signup(UserSignupRequest req) {
        //중복 체크 (기존 회원 존재시 예외 발생)
        userRepository.findByUserNameOrEmailOrPhoneNumber(req.getUserName(), req.getPhoneNumber(),req.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("입력한 정보로 이미 가입된 회원이 존재합니다.");
                });

        //회원 저장
        userRepository.save(new User(req, req.getPassword()));
    }
}
