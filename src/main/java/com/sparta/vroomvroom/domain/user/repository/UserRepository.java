package com.sparta.vroomvroom.domain.user.repository;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
}
