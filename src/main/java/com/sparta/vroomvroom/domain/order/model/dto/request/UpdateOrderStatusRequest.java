package com.sparta.vroomvroom.domain.order.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateOrderStatusRequest {

    @NotBlank(message = "주문 상태는 필수입니다.")
    private String orderStatus;
}