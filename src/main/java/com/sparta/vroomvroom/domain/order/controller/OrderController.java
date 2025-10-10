package com.sparta.vroomvroom.domain.order.controller;

import com.sparta.vroomvroom.domain.order.model.dto.request.CancelOrderRequest;
import com.sparta.vroomvroom.domain.order.model.dto.request.CreateOrderRequest;
import com.sparta.vroomvroom.domain.order.model.dto.response.CompanyOrderListResponse;
import com.sparta.vroomvroom.domain.order.model.dto.response.OrderDetailResponse;
import com.sparta.vroomvroom.domain.order.model.dto.response.OrderListResponse;
import com.sparta.vroomvroom.domain.order.service.OrderService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public BaseResponse<Void> createOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        orderService.createOrder(userDetails.getUser(), request);
        return new BaseResponse<>();
    }

    @GetMapping("/orders/{orderId}")
    public BaseResponse<OrderDetailResponse> getOrderDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID orderId
    ) {
        OrderDetailResponse response = orderService.getOrderDetail(
                userDetails.getUser(),
                orderId
        );
        return new BaseResponse<>(response);
    }

    @GetMapping("/orders")
    public BaseResponse<OrderListResponse> getOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String orderStatus
    ) {
        OrderListResponse response = orderService.getOrders(
                userDetails.getUser(),
                page,
                size,
                orderStatus
        );
        return new BaseResponse<>(response);
    }

    @DeleteMapping("/orders/{orderId}")
    public BaseResponse<Void> cancelOrder(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID orderId,
            @Valid @RequestBody CancelOrderRequest request
    ) {
        orderService.cancelOrder(userDetails.getUser(), orderId, request);
        return new BaseResponse<>();
    }

    @GetMapping("/companies/{companyId}/orders")
    public BaseResponse<CompanyOrderListResponse> getCompanyOrders(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID companyId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        CompanyOrderListResponse response = orderService.getCompanyOrders(
                userDetails.getUser(),
                companyId,
                page,
                size,
                orderStatus,
                startDate,
                endDate
        );
        return new BaseResponse<>(response);
    }
}
