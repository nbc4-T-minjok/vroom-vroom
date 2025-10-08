package com.sparta.vroomvroom.domain.user.service;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserUpdatedRequest;
import com.sparta.vroomvroom.domain.user.model.dto.response.UserDetailResponse;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //아이디, 전화번호, 이메일 셋 중 하나라도 기존 회원의 값과 중복되면 가입 불가
    //중복 체크 -> 회원 생성 처리 -> DB insert
    public void signup(UserSignupRequest req) {
        //중복 체크 (기존 회원 존재시 예외 발생)
        userRepository.findByUserNameOrEmailOrPhoneNumber(req.getUserName(), req.getEmail(), req.getPhoneNumber())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("입력한 정보로 이미 가입된 회원이 존재합니다.");
                });

        //회원 저장
        User user = new User(req, passwordEncoder.encode(req.getPassword()));
        user.create(req.getUserName());
        userRepository.save(user);
    }

    //회원 정보 조회
    public UserDetailResponse getUser(String userName) {
        User user = findUser(userName);
        return new UserDetailResponse(user);
    }

    //회원정보 수정
    //더티체킹을 통한 업테이트 사용 -> 영속성 컨텍스트 필요 (Transactional)
    @Transactional
    public void updateUser(String userName, UserUpdatedRequest req) {
        User user = findUser(userName);

        // 닉네임 업데이트
        if (req.getNickName() != null) {
            //더티 체킹 활용한 업데이트
            user.setNickName(req.getNickName());
        }

        // 전화번호 업데이트
        if (req.getPhoneNumber() != null && !req.getPhoneNumber().equals(user.getPhoneNumber())) {
            //중복여부 파악 후 더티 체킹 활용
            boolean exists = userRepository.existsByPhoneNumber(req.getPhoneNumber());
            if (exists) {
                throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
            }
            user.setPhoneNumber(req.getPhoneNumber());
        }

        // 이메일 업데이트
        if (req.getEmail() != null && !req.getEmail().equals(user.getEmail())) {
            //중복여부 파악 후 더티 체킹 활용
            boolean exists = userRepository.existsByEmail(req.getEmail());
            if (exists) {
                throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
            }
            user.setEmail(req.getEmail());
        }

    }


    //---- 유틸 및 재사용 함수

    //아이디로 유저 조회 후 예외 처리
    public User findUser(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new EntityNotFoundException("일치하는 회원이 존재하지 않습니다.")
        );
        return user;
    }
}
