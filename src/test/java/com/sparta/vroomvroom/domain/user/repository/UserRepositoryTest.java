package com.sparta.vroomvroom.domain.user.repository;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.conmon.constants.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private User createUser() {
        User user = new User();
        user.setUserName("user1234");
        user.setPassword("Abcd1234!");
        user.setNickName("닉네임");
        user.setType(UserType.INAPP);
        user.setName("홍길동");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setGender("남");
        user.setPhoneNumber("010-1234-5678");
        user.setEmail("test@example.com");
        user.setRole(UserRole.ROLE_CUSTOMER);
        user.create(user.getUserName());
        return userRepository.save(user);
    }

    @DisplayName("userName으로만 기존 회원이 존재할 때 조회 성공")
    @Test
    void findByUserNameOnly() {
        // given
        createUser();

        // when
        Optional<User> found = userRepository.findByUserNameOrEmailOrPhoneNumber(
                "user1234", "wrong@example.com", "010-9999-9999");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo("user1234");
    }

    @DisplayName("email로만 기존 회원이 존재할 때 조회 성공")
    @Test
    void findByEmailOnly() {
        // given
        createUser();

        // when
        Optional<User> found = userRepository.findByUserNameOrEmailOrPhoneNumber(
                "wrongUser", "test@example.com", "010-9999-9999");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
    }

    @DisplayName("phoneNumber로만 기존 회원이 존재할 때 조회 성공")
    @Test
    void findByPhoneNumberOnly() {
        // given
        createUser();

        // when
        Optional<User> found = userRepository.findByUserNameOrEmailOrPhoneNumber(
                "wrongUser", "wrong@example.com", "010-1234-5678");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getPhoneNumber()).isEqualTo("010-1234-5678");
    }

    @DisplayName("userName, email, phoneNumber 모두 일치하지 않으면 조회 실패")
    @Test
    void findByNoMatch() {
        // given
        createUser();

        // when
        Optional<User> found = userRepository.findByUserNameOrEmailOrPhoneNumber(
                "notExist", "none@example.com", "010-0000-0000");

        // then
        assertThat(found).isEmpty();
    }
}