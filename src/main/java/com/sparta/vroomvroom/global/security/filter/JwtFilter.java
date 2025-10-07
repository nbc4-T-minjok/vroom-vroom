package com.sparta.vroomvroom.global.security.filter;

import com.sparta.vroomvroom.domain.user.repository.BlackListRepository;
import com.sparta.vroomvroom.global.security.UserDetailsServiceImpl;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT Filter")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final BlackListRepository blackListRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //토큰 추출
        String token = jwtUtil.getTokenFromCookie(request);

        //토큰 검증
        if(token == null || !jwtUtil.validateToken(token)) {
            log.warn("JWT 토큰이 존재하지 않거나 유효하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //블랙리스트 확인
        if (blackListRepository.findByToken(token).isPresent()) {
            log.warn("만료된 JWT 토큰입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰에서 값 추출
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        String userName = claims.getSubject();

        //인증 시도
        try {
            //성공시 인증객체 생성
            setAuthentication(userName);
        } catch (Exception e) {
            //실패시 로그
            log.error("JWT 토큰을 통한 인증이 실패했습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //정상 인증이 끝나면 다음 필터 진행
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
