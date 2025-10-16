package com.sparta.vroomvroom.domain.manager.model.dto.response;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.global.conmon.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ManagerOrderResponse {

    private UUID orderId;
    private Long userId;
    private UUID companyId;
    private OrderStatus orderStatus;

    public ManagerOrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.orderStatus = order.getOrderStatus();
        this.companyId = order.getCompany().getCompanyId();
        this.userId = order.getUser().getUserId();
    }
}
