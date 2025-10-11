package com.sparta.vroomvroom.domain.cart.model.dto.response;

import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
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

    public static CartMenuResponse from(CartMenu cartMenu, Menu menu, Company company) {
        return new CartMenuResponse(
                cartMenu.getCartMenuId(),
                menu.getMenuId(),
                menu.getMenuName(),
                menu.getMenuPrice(),
                cartMenu.getMenuAmount(),
                menu.getMenuImage(),
                company.getCompanyId(),
                company.getCompanyName()
        );
    }
}