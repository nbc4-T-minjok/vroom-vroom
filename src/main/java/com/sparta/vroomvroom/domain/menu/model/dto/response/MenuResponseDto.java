package com.sparta.vroomvroom.domain.menu.model.dto.response;

import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MenuResponseDto {

    private UUID menuId;
    private String companyId;
    private String menuName;
    private String menuGroup;
    private Integer menuPrice;
    private String menuImage;
    private String menuDescription;
    private String menuStatus;
    private Boolean isVisible;

    public MenuResponseDto(Menu menu) {
        this.menuId = menu.getMenuId();
        this.menuName = menu.getMenuName();
        this.menuGroup = menu.getMenuGroup();
        this.menuPrice = menu.getMenuPrice();
        this.menuImage = menu.getMenuImage();
        this.menuDescription = menu.getMenuDescription();
        this.menuStatus = menu.getMenuStatus().getStatus();
        this.isVisible = menu.getIsVisible();
    }
}
