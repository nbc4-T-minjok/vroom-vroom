package com.sparta.vroomvroom.global.security.handler;

import com.sparta.vroomvroom.domain.user.model.entity.BlackList;
import com.sparta.vroomvroom.domain.user.repository.BlackListRepository;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final BlackListRepository blackListRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtUtil.getTokenFromCookie(request);
        //토큰 유무
        if (token == null) {
            request.setAttribute("logoutError", "요청이 실패했습니다. 로그인이 되어있지 않습니다.");
            return;
        }
        //토큰 만료 여부
        if (!jwtUtil.validateToken(token)) {
            request.setAttribute("logoutError", "요청이 실패했습니다. 만료된 토큰입니다.");
        }

        //비정상 토큰 & 정상 토큰 전부 블랙리스트 등록
        //Todo: 블랙리스트 조회로 DB I/O가 과도하게 발생중 개선 필요
        if (!blackListRepository.findByToken(token).isPresent()) {
            blackListRepository.save(new BlackList(token));
        }

        // 쿠키 삭제
        Cookie cookie = new Cookie("AToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
