package com.sparta.vroomvroom.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//logout성공 시 자동으로 /login이나 다른 url로 리다이렉트를 방지하는 핸들러
//logout성공을 감지해서 가로챔
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        BaseResponse baseResponse = new BaseResponse<>();
        response.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));
        response.getWriter().flush();
    }
}
