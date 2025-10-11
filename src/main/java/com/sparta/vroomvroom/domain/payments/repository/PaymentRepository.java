package com.sparta.vroomvroom.domain.payments.repository;

import com.sparta.vroomvroom.domain.payments.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrder_OrderId(UUID orderId);
}
