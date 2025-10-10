package com.sparta.vroomvroom.global.security.handler;

import com.sparta.vroomvroom.global.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//인증은 성공했지만 시큐리티 단에서 URI매핑 등 권한으로 인해 실패할 경우 처리 핸들러
//로그인 성공 이후 권한이 부족할 경우 처리
//requestMatcher에서 hasRole()등 권한 처리가 실패하면 동작
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ResponseUtil responseUtil;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        responseUtil.sendErrorResponse(response, "요청이 실패했습니다. 접근 권한이 없습니다.", HttpServletResponse.SC_FORBIDDEN);
    }
}