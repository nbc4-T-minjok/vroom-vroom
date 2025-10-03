//package com.sparta.vroomvroom.global.security;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class AuditorAwareImpl implements AuditorAware<String> {
//    @Override
//    public Optional<String> getCurrentAuditor() {
//        // JpaAuditing의 CreatedBy LastModifiedBy등 사용할때 여기서 값 가져오라고 명시
//        // 시큐리티 컨텍스트에서 로그인한 사용자 ID 반환
//        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
//    }
//}
