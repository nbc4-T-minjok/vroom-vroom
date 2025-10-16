package com.sparta.vroomvroom.domain.ai.repository;
import com.sparta.vroomvroom.domain.ai.model.entity.AiApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeminiRepository extends JpaRepository<AiApiLog, UUID> {
    AiApiLog findTopByOrderByCreatedAtDesc();
}