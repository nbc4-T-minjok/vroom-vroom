package com.sparta.vroomvroom.global.config;

import com.sparta.vroomvroom.domain.user.repository.BlackListRepository;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.security.UserDetailsServiceImpl;
import com.sparta.vroomvroom.global.security.filter.JwtFilter;
import com.sparta.vroomvroom.global.security.filter.LoginFilter;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final BlackListRepository blackListRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
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
        LoginFilter loginFilter = new LoginFilter(jwtUtil);
        loginFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return loginFilter;
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
                        .requestMatchers("/api/v1/users/login","/api/v1/users/signup").permitAll()
                        .requestMatchers("/api/v1/users/login/test").hasRole(UserRole.ROLE_MANAGER.getRole())
                        .anyRequest().denyAll()
                )

                // 로그아웃 관련
                .logout(logout -> logout
                        .logoutUrl("/api/v1/users/logout")
                        .deleteCookies("AToken")
                        .invalidateHttpSession(true)
                );

        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
