package com.sparta.vroomvroom.domain.order.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CancelOrderRequest {

    @NotBlank(message = "취소 사유는 필수입니다.")
    private String cancelReason;
}