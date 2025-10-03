package com.sparta.vroomvroom.domain.order.model.entity;

import com.sparta.vroomvroom.domain.address.model.entity.Address;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address deliveryAddress; // address -> deliveryAddress

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "order_price", nullable = false)
    private int orderPrice;

    @Column(name = "order_request", length = 255)
    private String orderRequest;

    @Column(name = "cancel_reason", length = 255)
    private String cancelReason;

    @Column(name = "order_status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "zip_code", nullable = false, length = 50)
    private String zipCode;

    @Column(name = "address_name", nullable = false, length = 20)
    private String addressName;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "detail_address", nullable = false, length = 50)
    private String detailAddress;
}
