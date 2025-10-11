package com.sparta.vroomvroom.domain.payments.controller;

import com.sparta.vroomvroom.domain.payments.model.dto.response.PaymentResponse;
import com.sparta.vroomvroom.domain.payments.service.PaymentService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/orders/{orderId}/payments")
    public BaseResponse<PaymentResponse> getPayment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID orderId
    ) {
        PaymentResponse response = paymentService.getPayment(userDetails.getUser(), orderId);
        return new BaseResponse<>(response);
    }
}
