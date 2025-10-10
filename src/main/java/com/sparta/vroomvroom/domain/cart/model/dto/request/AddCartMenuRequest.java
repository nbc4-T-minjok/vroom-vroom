// AddCartMenuRequest.java
package com.sparta.vroomvroom.domain.cart.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class AddCartMenuRequest {

    @NotNull(message = "메뉴 ID는 필수입니다.")
    private UUID menuId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer menuAmount;
}