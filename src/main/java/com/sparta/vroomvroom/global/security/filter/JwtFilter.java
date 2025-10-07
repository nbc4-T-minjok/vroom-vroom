package com.sparta.vroomvroom.global.security.filter;

import com.sparta.vroomvroom.domain.user.model.entity.BlackList;
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

        //토큰 유무 판단
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            //블랙리스트 조회
            if (blackListRepository.findByToken(token).isPresent()) {
                filterChain.doFilter(request, response);
                return;
            }

            //유효하지 않은 토큰은 블랙리스트 저장
            if(!jwtUtil.validateToken(token)) {
                blackListRepository.save(new BlackList(token));
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.getUserInfoFromToken(token);
            String userName = claims.getSubject();

            setAuthentication(userName);
        } catch (Exception e) {
            // 토큰 파싱 실패 등 모든 예외도 블랙리스트에 등록
            blackListRepository.save(new BlackList(token));
            filterChain.doFilter(request, response);
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
