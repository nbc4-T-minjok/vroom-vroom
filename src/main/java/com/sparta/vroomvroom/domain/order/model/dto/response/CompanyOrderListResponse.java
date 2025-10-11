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
public class CompanyOrderListResponse {
    private List<CompanyOrderSummaryResponse> orders;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    public static CompanyOrderListResponse from(Page<Order> orderPage, List<CompanyOrderSummaryResponse> orders) {
        return new CompanyOrderListResponse(
                orders,
                orderPage.getTotalPages(),
                orderPage.getTotalElements(),
                orderPage.getNumber() + 1,
                orderPage.getSize()
        );
    }

    @Getter
    @AllArgsConstructor
    public static class CompanyOrderSummaryResponse {
        private UUID orderId;
        private Long userId;
        private String userName;
        private Integer orderPrice;
        private String orderRequest;
        private String orderStatus;
        private String deliveryAddress;
        private String phoneNumber;
        private LocalDateTime createdAt;

        public static CompanyOrderSummaryResponse from(Order order) {
            return new CompanyOrderSummaryResponse(
                    order.getOrderId(),
                    order.getUser().getUserId(),
                    order.getUser().getUserName(),
                    order.getOrderPrice(),
                    order.getOrderRequest(),
                    order.getOrderStatus().name(),
                    order.getAddress() + " " + order.getDetailAddress(),
                    order.getUser().getPhoneNumber(),
                    order.getCreatedAt()
            );
        }
    }
}