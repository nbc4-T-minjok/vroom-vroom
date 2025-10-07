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
        if(token != null && jwtUtil.validateToken(token)) {
            //Todo: 블랙리스트 조회로 DB I/O가 과도하게 발생중 개선 필요

            // 블랙리스트에 없는 경우에만 등록
            if (!blackListRepository.findByToken(token).isPresent()) {
                BlackList blackList = new BlackList(token);
                blackListRepository.save(blackList);
            }

            // 쿠키 삭제
            Cookie cookie = new Cookie("AToken", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }else{
            //로그아웃은 예외 발생이 아니면 무조건 성공 핸들러 요청
            //응답 구분은 성공 핸들러에서 처리
            request.setAttribute("logoutError", "잘못된 접근입니다. 로그인 되어 있지 않습니다.");
        }
    }
}
