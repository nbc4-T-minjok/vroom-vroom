package com.sparta.vroomvroom.domain.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sparta.vroomvroom.domain.ai.model.entity.AiApiLog;
import com.sparta.vroomvroom.domain.ai.repository.GeminiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    private final GeminiRepository geminiRepository;
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateMenuDescription(String menuName) {

        // 환경 변수 확인
        if (apiKey == null || apiKey.isBlank() || apiUrl == null || apiUrl.isBlank()) {
            log.error("Gemini API Key 또는 URL이 주입되지 않았습니다. application.yml 설정을 확인하세요.");
            throw new IllegalStateException("Gemini API 설정이 누락되었습니다.");
        }

        // 프롬프트 생성
        String prompt = String.format(
                "'%s'이라는 메뉴에 대해, 고객을 끌 수 있는 매력적인 메뉴 설명을 생성해줘. 답변을 최대한 간결하게 50자 이하로.",
                menuName
        );

        // Gemini API의 요청 Body 구조에 맞게 Map 생성
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of(
                                "text", prompt
                        ))
                ))
        );

        try {
            log.info("Gemini API 요청 시작: {}", prompt);

            // API 호출
            String responseText = webClient.post()
                    // Gemini는 API 키를 URL 파라미터로 전달
                    .uri(apiUrl + "?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    // Gemini 응답 구조에 맞게 JsonNode 파싱
                    .map(json -> json
                            .get("candidates")
                            .get(0)
                            .get("content")
                            .get("parts")
                            .get(0)
                            .get("text")
                            .asText())
                    .block(); // 동기 처리를 위해 block()

            log.info("Gemini API 응답 수신: {}", responseText);

            // AI 요청/응답 로그 저장
            AiApiLog requestLog = AiApiLog.builder()
                    .prompt(prompt)
                    .response(responseText)
                    .createdAt(LocalDateTime.now())
                    .build();

            geminiRepository.save(requestLog);
            log.info("AI 요청 기록 저장 완료");

            return responseText;

        } catch (WebClientResponseException e) {
            log.error("Gemini API 호출 실패 - 상태코드: {}, 응답본문: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Gemini API Error: " + e.getStatusCode(), e);

        } catch (Exception e) {
            log.error("AI 설명 생성 중 예외 발생: ", e);
            throw new RuntimeException("AI description generation failed", e);
        }
    }
}
