package com.sparta.vroomvroom.domain.order.model.entity;

import com.sparta.vroomvroom.domain.address.model.entity.Address;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
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

    public static Order createOrder(
            User user,
            Address address,
            Company company,
            Integer orderPrice,
            String orderRequest,
            String createdBy
    ) {
        Order order = new Order();
        order.user = user;
        order.deliveryAddress = address;
        order.company = company;
        order.orderPrice = orderPrice;
        order.orderRequest = orderRequest;
        order.orderStatus = OrderStatus.PENDING;
        order.zipCode = address.getZipCode();
        order.addressName = address.getAddressName();
        order.address = address.getAddress();
        order.detailAddress = address.getDetailAddress();

        order.create(createdBy);
        return order;
    }

    public void cancel(String cancelReason, String updatedBy) {
        this.orderStatus = OrderStatus.CANCELED;
        this.cancelReason = cancelReason;
        this.update(updatedBy);
    }

    public void updateStatus(OrderStatus newStatus, String updatedBy) {
        this.orderStatus = newStatus;
        this.update(updatedBy);
    }
}
