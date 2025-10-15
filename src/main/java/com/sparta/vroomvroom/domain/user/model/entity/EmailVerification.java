package com.sparta.vroomvroom.domain.user.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_verification")
@Getter
@NoArgsConstructor
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_verified")
    private boolean isVerified;

    public EmailVerification(String email, String code, LocalDateTime expiresAt) {
        this.email = email;
        this.code = code;
        this.expiresAt = expiresAt;
        this.isVerified = false;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public void verify() {
        this.isVerified = true;
    }
}
