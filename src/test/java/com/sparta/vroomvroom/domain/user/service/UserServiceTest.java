package com.sparta.vroomvroom.domain.user.service;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.conmon.constants.UserType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserSignupRequest req;

    @BeforeEach
    void setUp() {
        req = new UserSignupRequest();
        req.setUserName("user1234");
        req.setPassword("Abcd1234!");
        req.setNickName("닉네임");
        req.setType(UserType.INAPP);
        req.setName("홍길동");
        req.setBirthDate(LocalDate.of(1990, 1, 1));
        req.setGender("남");
        req.setPhoneNumber("010-1234-5678");
        req.setEmail("test@example.com");
        req.setRole(UserRole.ROLE_CUSTOMER);
    }

    @DisplayName("성공 케이스: 입력한 정보로 가입된 기존 회원이 없어서 정상 가입 성공")
    @Test
    void userSignupSuccess() {
        // given
        when(userRepository.findByUserNameOrEmailOrPhoneNumber(
                req.getUserName(), req.getPhoneNumber(), req.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(req.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        // when
        assertDoesNotThrow(() -> userService.signup(req));

        // then
        // encode 호출 여부, save 호출 여부 검증
        verify(passwordEncoder, times(1)).encode(req.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @DisplayName("실패 케이스: 입력한 정보로 이미 가입된 회원이 있어서 가입 실패")
    @Test
    void userSignupFailAlreadyExist() {
        // given
        // 비어있지 않은 객체를 응답하도록 정의
        when(userRepository.findByUserNameOrEmailOrPhoneNumber(
                req.getUserName(), req.getPhoneNumber(), req.getEmail())).thenReturn(Optional.of(new User()));
        // when & then
        // 예외 발생하는지 검증
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.signup(req));

        // 에러 메시지가 정확한지, save가 호출되었는지 검증
        assertEquals("입력한 정보로 이미 가입된 회원이 존재합니다.", e.getMessage());
        verify(userRepository,never()).save(any(User.class));

    }
}