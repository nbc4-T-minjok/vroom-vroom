package com.sparta.vroomvroom.domain.user.repository;

import com.sparta.vroomvroom.domain.user.model.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {
    Optional<EmailVerification> findByEmail(String email);
    Optional<EmailVerification> findByEmailAndCode(String email, String code);
    void deleteAllByEmail(String email);
}
