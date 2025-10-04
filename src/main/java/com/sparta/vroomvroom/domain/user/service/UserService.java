package com.sparta.vroomvroom.domain.user.service;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //아이디, 전화번호, 이메일 셋 중 하나라도 기존 회원의 값과 중복되면 가입 불가
    //중복 체크 -> 회원 생성 처리 -> DB insert
    public void signup(UserSignupRequest req) {
        //중복 체크 (기존 회원 존재시 예외 발생)
        userRepository.findByUserNameOrEmailOrPhoneNumber(req.getUserName(), req.getPhoneNumber(),req.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("입력한 정보로 이미 가입된 회원이 존재합니다.");
                });

        //회원 저장
        User user = new User(req, passwordEncoder.encode(req.getPassword()));
        //todo: 시큐리티 도입 후 자동화 하도록 변경 (추후 삭제)
        user.create(req.getUserName());
        userRepository.save(user);
    }
}
