package com.sparta.vroomvroom.domain.ai.model.dto.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gemini_request_dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "text")
    private String prompt;

    @Column(columnDefinition = "text")
    private String response;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
