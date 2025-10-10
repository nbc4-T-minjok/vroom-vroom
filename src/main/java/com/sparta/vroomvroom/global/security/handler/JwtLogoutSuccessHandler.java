package com.sparta.vroomvroom.global.security.handler;

import com.sparta.vroomvroom.global.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
//logout성공 시 자동으로 /login이나 다른 url로 리다이렉트를 방지하는 핸들러
//logout성공을 감지해서 가로챔
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ResponseUtil responseUtil;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        Object errorMessage = request.getAttribute("logoutError");
        if (errorMessage != null) {
            // 쿠키 없거나 비정상 접근
            responseUtil.sendErrorResponse(response, errorMessage.toString(), HttpServletResponse.SC_BAD_REQUEST);
        } else {
            // 정상 로그아웃
            responseUtil.sendSuccessResponse(response);
        }
    }
}
