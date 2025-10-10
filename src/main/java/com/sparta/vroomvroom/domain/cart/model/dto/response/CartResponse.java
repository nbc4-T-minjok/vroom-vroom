package com.sparta.vroomvroom.domain.cart.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CartResponse {

    private UUID cartId;
    private Boolean isEmpty;
    private Integer totalPrice;
    private List<CartMenuResponse> cartMenus;
}