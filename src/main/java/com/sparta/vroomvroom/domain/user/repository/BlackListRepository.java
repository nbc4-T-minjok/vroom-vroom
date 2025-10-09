package com.sparta.vroomvroom.domain.user.repository;

import com.sparta.vroomvroom.domain.user.model.entity.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BlackListRepository extends JpaRepository<BlackList, UUID> {
    Optional<BlackList> findByToken(String token);
}
