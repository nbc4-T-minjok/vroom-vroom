package com.sparta.vroomvroom.domain.order.model.dto.response;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderListResponse {
    private List<OrderSummaryResponse> orders;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    public static OrderListResponse from(Page<Order> orderPage, List<OrderSummaryResponse> orders) {
        return new OrderListResponse(
                orders,
                orderPage.getTotalPages(),
                orderPage.getTotalElements(),
                orderPage.getNumber() + 1,
                orderPage.getSize()
        );
    }

    @Getter
    @AllArgsConstructor
    public static class OrderSummaryResponse {
        private UUID orderId;
        private UUID companyId;
        private String companyName;
        private Integer orderPrice;
        private String orderRequest;
        private String orderStatus;
        private String addressName;
        private LocalDateTime createdAt;

        public static OrderSummaryResponse from(Order order) {
            return new OrderSummaryResponse(
                    order.getOrderId(),
                    order.getCompany().getCompanyId(),
                    order.getCompany().getCompanyName(),
                    order.getOrderPrice(),
                    order.getOrderRequest(),
                    order.getOrderStatus().name(),
                    order.getAddressName(),
                    order.getCreatedAt()
            );
        }
    }
}