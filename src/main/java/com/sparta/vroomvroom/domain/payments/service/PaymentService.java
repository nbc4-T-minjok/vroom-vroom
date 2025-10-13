package com.sparta.vroomvroom.domain.payments.service;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.repository.OrderRepository;
import com.sparta.vroomvroom.domain.payments.model.dto.response.PaymentResponse;
import com.sparta.vroomvroom.domain.payments.model.entity.Payment;
import com.sparta.vroomvroom.domain.payments.repository.PaymentRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(User user, UUID orderId) {
        // 1. 주문 존재 확인
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        // 2. 권한 확인 (본인 주문인지)
        if (!order.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 주문에 대한 권한이 없습니다.");
        }

        // 3. 결제 정보 조회
        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        // 4. Response 생성
        return PaymentResponse.from(payment);
    }
}