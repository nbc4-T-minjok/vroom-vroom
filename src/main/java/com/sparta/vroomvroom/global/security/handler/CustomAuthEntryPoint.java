package com.sparta.vroomvroom.global.security.handler;

import com.sparta.vroomvroom.global.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//스프링 시큐리티 단에서 인증 실패시 응답 처리 핸들러
//토큰이 없거나, 만료되거나, 위조된 경우 처리
//requestMatcher에서 authenticated()를 통과하지 못하면 동작
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ResponseUtil responseUtil;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        responseUtil.sendErrorResponse(response, "요청이 실패했습니다. 인증되지 않은 사용자입니다.",HttpServletResponse.SC_UNAUTHORIZED);
    }
}