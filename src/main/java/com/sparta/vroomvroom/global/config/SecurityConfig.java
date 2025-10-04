package com.sparta.vroomvroom.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //보안 옵션
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())

                // 인증 인가 규칙
                // 임시로 전부 허용
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 로그아웃 관련
                .logout(logout -> logout
                        .logoutUrl("/api/v1/logout")
                        .deleteCookies("AToken")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

}
