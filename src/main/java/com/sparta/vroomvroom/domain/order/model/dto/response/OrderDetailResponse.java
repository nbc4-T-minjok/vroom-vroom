package com.sparta.vroomvroom.domain.order.model.dto.response;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.model.entity.OrderMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private UUID orderId;
    private Long userId;
    private String userName;
    private String userPhoneNumber;
    private UUID companyId;
    private String companyName;
    private String companyPhoneNumber;
    private Integer orderPrice;
    private String orderRequest;
    private String cancelReason;
    private String orderStatus;
    private String addressName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private List<OrderMenuResponse> orderMenus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @AllArgsConstructor
    public static class OrderMenuResponse {
        private UUID orderMenuId;
        private UUID menuId;
        private String menuName;
        private Integer menuPrice;
        private Integer menuAmount;
        private String menuImage;
    }

    public static OrderDetailResponse from(Order order, List<OrderMenu> orderMenus) {
        List<OrderMenuResponse> orderMenuResponses = orderMenus.stream()
                .map(om -> new OrderMenuResponse(
                        om.getOrderMenuId(),
                        om.getMenu().getMenuId(),
                        om.getMenu().getName(),
                        om.getMenuPrice(),
                        om.getMenuAmount(),
                        om.getMenu().getMenuImage()
                ))
                .collect(Collectors.toList());

        return new OrderDetailResponse(
                order.getOrderId(),
                order.getUser().getUserId(),
                order.getUser().getUserName(),
                order.getUser().getPhoneNumber(),
                order.getCompany().getCompanyId(),
                order.getCompany().getCompanyName(),
                order.getCompany().getPhoneNumber(),
                order.getOrderPrice(),
                order.getOrderRequest(),
                order.getCancelReason(),
                order.getOrderStatus().name(),
                order.getAddressName(),
                order.getAddress(),
                order.getDetailAddress(),
                order.getZipCode(),
                orderMenuResponses,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}