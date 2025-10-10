package com.sparta.vroomvroom.domain.order.repository;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.global.conmon.constants.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    // TODO: JPQL로 변경하기

    // 사용자별 전체 주문 목록 (최신순)
    Page<Order> findByUser_UserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // 사용자별 + 주문 상태 필터링 (최신순)
    Page<Order> findByUser_UserIdAndOrderStatusOrderByCreatedAtDesc(
            Long userId,
            OrderStatus orderStatus,
            Pageable pageable
    );

    // 업체별 전체 주문 (최신순)
    Page<Order> findByCompany_CompanyIdOrderByCreatedAtDesc(UUID companyId, Pageable pageable);

    // 업체별 + 주문 상태 필터링
    Page<Order> findByCompany_CompanyIdAndOrderStatusOrderByCreatedAtDesc(
            UUID companyId,
            OrderStatus orderStatus,
            Pageable pageable
    );

    // 업체별 + 날짜 범위
    Page<Order> findByCompany_CompanyIdAndCreatedAtBetweenOrderByCreatedAtDesc(
            UUID companyId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    // 업체별 + 주문 상태 + 날짜 범위
    Page<Order> findByCompany_CompanyIdAndOrderStatusAndCreatedAtBetweenOrderByCreatedAtDesc(
            UUID companyId,
            OrderStatus orderStatus,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
