package com.sparta.vroomvroom.domain.order.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "배송지 ID는 필수입니다.")
    private UUID userAddressId;

    private String orderRequest;

    @NotBlank(message = "결제 방법은 필수입니다.")
    private String paymentMethod;
}