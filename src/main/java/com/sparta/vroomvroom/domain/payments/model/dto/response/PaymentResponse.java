package com.sparta.vroomvroom.domain.payments.model.dto.response;

import com.sparta.vroomvroom.domain.payments.model.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private UUID paymentId;
    private UUID orderId;
    private String paymentMethod;
    private Integer paymentPrice;
    private String paymentStatus;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getPaymentMethod().name(),
                payment.getPaymentPrice(),
                payment.getPaymentStatus().name(),
                payment.getPaidAt(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
