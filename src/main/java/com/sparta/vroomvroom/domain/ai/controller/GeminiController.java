package com.sparta.vroomvroom.domain.ai.controller;

import com.sparta.vroomvroom.domain.ai.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/menu-description")
    public String generateDescription(@RequestParam String menuName) {

        return geminiService.generateMenuDescription(menuName);
    }

}
