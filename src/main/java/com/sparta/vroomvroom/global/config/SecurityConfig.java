package com.sparta.vroomvroom.global.config;

import com.sparta.vroomvroom.domain.user.repository.BlackListRepository;
import com.sparta.vroomvroom.global.security.UserDetailsServiceImpl;
import com.sparta.vroomvroom.global.security.filter.JwtFilter;
import com.sparta.vroomvroom.global.security.handler.CustomAccessDeniedHandler;
import com.sparta.vroomvroom.global.security.handler.CustomAuthEntryPoint;
import com.sparta.vroomvroom.global.security.handler.JwtLogoutHandler;
import com.sparta.vroomvroom.global.security.handler.JwtLogoutSuccessHandler;
import com.sparta.vroomvroom.global.security.filter.LoginFilter;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import com.sparta.vroomvroom.global.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final BlackListRepository blackListRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ResponseUtil responseUtil;
    private final CustomAuthEntryPoint customAuthEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtFilter jwtFilter() {
        JwtFilter filter = new JwtFilter(jwtUtil,blackListRepository,userDetailsService);
        return filter;
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(jwtUtil,responseUtil);
        loginFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return loginFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtLogoutHandler jwtLogoutHandler, JwtLogoutSuccessHandler jwtLogoutSuccessHandler) throws Exception {
        http
                //보안 옵션
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())

                //인증 인가 규칙
                //Todo: v1개발 진척도에 따라 세부 조정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/users/login","/api/v1/users/signup").permitAll()
                        .requestMatchers("/api/v1/users/logout").authenticated()
                        .anyRequest().permitAll()
                )

                // 로그아웃 관련
                .logout(logout -> logout
                        .logoutUrl("/api/v1/users/logout")
                        .addLogoutHandler(jwtLogoutHandler)
                        .logoutSuccessHandler(jwtLogoutSuccessHandler)
                        .invalidateHttpSession(true)
                )

                //시큐리티 단 통합 예외처리
                .exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
        );

        //필터 추가 및 교체
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
