package com.sparta.vroomvroom.domain.ai.controller;

import com.sparta.vroomvroom.domain.ai.model.entity.AiApiLog;
import com.sparta.vroomvroom.domain.ai.repository.GeminiRepository;
import com.sparta.vroomvroom.domain.ai.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    private final GeminiService geminiService;
    private final GeminiRepository geminiRepository;

    @PostMapping("/menu-description/preview")
    public ResponseEntity<Map<String, String>> generatePreview(@RequestBody Map<String, String> request) {
        String menuName = request.get("menuName");


        String aiDescription = geminiService.generateMenuDescription(menuName);

        AiApiLog log = geminiRepository.findTopByOrderByCreatedAtDesc();

        Map<String, String> response = Map.of(
                "menuName", menuName,
                "aiDescription", aiDescription
        );

        return ResponseEntity.ok(response);
    }
}


