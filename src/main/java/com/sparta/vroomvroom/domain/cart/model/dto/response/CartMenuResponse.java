package com.sparta.vroomvroom.domain.cart.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CartMenuResponse {

    private UUID cartMenuId;
    private UUID menuId;
    private String menuName;
    private Integer menuPrice;
    private Integer menuAmount;
    private String menuImage;
    private UUID companyId;
    private String companyName;
}