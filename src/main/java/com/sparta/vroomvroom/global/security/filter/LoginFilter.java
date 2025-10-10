package com.sparta.vroomvroom.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserLoginRequest;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import com.sparta.vroomvroom.global.utils.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "Login Filter")
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ResponseUtil responseUtil;

    public LoginFilter(JwtUtil jwtUtil, ResponseUtil responseUtil) {
        this.jwtUtil = jwtUtil;
        this.responseUtil = responseUtil;
        setFilterProcessesUrl("/api/v1/users/login");
    }


    //토큰이 없는 경우 동작, 있는 상태에서 시도시 실패 응답
    //아이디, 비밀번호로 인증수행
    //성공시 JWT토큰을 발행하고 쿠키에 담아 응답
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 토큰이 있는 상태로 로그인 시도시 에러 응답
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            responseUtil.sendErrorResponse(response,"잘못된 접근 입니다. 이미 인증된 사용자입니다.", HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        try {
            UserLoginRequest userLoginRequest = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUserName(),
                            userLoginRequest.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            responseUtil.sendErrorResponse(response,"인증에 실패했습니다. 입력한 정보를 확인해주세요.",HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }

    @Override
    //성공시 쿠키에 토큰 세팅
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        Cookie cookie = jwtUtil.createToken(username, role);
        response.addCookie(cookie);
        //규격에 맞게 응답
        responseUtil.sendSuccessResponse(response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        //규격에 맞게 응답
        responseUtil.sendErrorResponse(response,"인증에 실패했습니다. 입력한 정보를 확인해주세요.", HttpServletResponse.SC_UNAUTHORIZED);
    }

}
