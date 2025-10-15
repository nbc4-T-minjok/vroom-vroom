package com.sparta.vroomvroom.domain.user.repository;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE (u.userName = :userName OR u.email = :email OR u.phoneNumber = :phoneNumber) AND u.isDeleted = false")
    Optional<User> findByUserNameOrEmailOrPhoneNumberAndNotDeleted(
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("phoneNumber") String phoneNumber
    );

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    Optional<User> findByUserNameAndIsDeletedFalse(String userName);

    List<User> findAllByIsDeletedFalse();
}