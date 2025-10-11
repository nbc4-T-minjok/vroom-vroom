package com.sparta.vroomvroom.domain.payments.model.entity;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.PaymentMethod;
import com.sparta.vroomvroom.global.conmon.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private UUID paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_method", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_price", nullable = false)
    private int paymentPrice;

    @Column(name = "payment_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    public static Payment createPayment(
            Order order,
            PaymentMethod paymentMethod,
            Integer paymentPrice,
            String createdBy
    ) {
        Payment payment = new Payment();
        payment.order = order;
        payment.paymentMethod = paymentMethod;
        payment.paymentPrice = paymentPrice;
        payment.paymentStatus = PaymentStatus.PAID;
        payment.paidAt = LocalDateTime.now();
        payment.create(createdBy);
        return payment;
    }

    public void refund(String updatedBy) {
        this.paymentStatus = PaymentStatus.REFUNDED;
        this.update(updatedBy);
    }
}
