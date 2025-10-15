package com.sparta.vroomvroom.domain.ai.controller;

import com.sparta.vroomvroom.domain.ai.model.entity.AiApiLog;
import com.sparta.vroomvroom.domain.ai.repository.GeminiRepository;
import com.sparta.vroomvroom.domain.ai.service.GeminiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "gemini", description = "AI 메뉴 설명 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @Operation(summary = "AI 메뉴 설명 생성 API", description = "메뉴 이름을 입력하면 Gemini가 메뉴 설명을 생성합니다.")
    @PostMapping("/menu-description")
    public ResponseEntity<Map<String, String>> generatePreview(@RequestBody Map<String, String> request) {
        String menuName = request.get("menuName");

        String aiDescription = geminiService.generateMenuDescription(menuName);

        Map<String, String> response = Map.of(
                "menuName", menuName,
                "aiDescription", aiDescription
        );

        return ResponseEntity.ok(response);
    }
}


