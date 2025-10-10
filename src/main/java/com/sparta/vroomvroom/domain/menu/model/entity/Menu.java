package com.sparta.vroomvroom.domain.menu.model.entity;

import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.order.model.entity.OrderMenu;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menus")
@Getter
@Setter
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id", columnDefinition = "uuid")
    private UUID menuId;

    // TODO: 업체 도메인 개발 완료후 Company 연관관계로 변경
    @Column(name = "company_id", nullable = false, columnDefinition = "uuid")
    private UUID companyId;

    @Column(name = "menu_name", nullable = false, length = 50)
    private String menuName;

    @Column(name = "menu_price", nullable = false)
    private Integer menuPrice;

    @Column(name = "menu_group", nullable = false, length = 20)
    private String menuGroup;

    @Column(name = "menu_image", length = 255)
    private String menuImage;

    @Column(name = "menu_description", columnDefinition = "text")
    private String menuDescription;

    @Column(name = "menu_status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private MenuStatus menuStatus;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by", nullable = false, length = 20)
    private String createdBy = "test";

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 20)
    private String deletedBy;



    @Builder
    public Menu(UUID companyId, MenuRequestDto dto) {
        this.companyId = companyId;
        this.menuName = dto.getMenuName();
        this.menuGroup = dto.getMenuGroup();
        this.menuPrice = dto.getMenuPrice();
        this.menuImage = dto.getMenuImage();
        this.menuDescription = dto.getMenuDescription();
        this.menuStatus = dto.getMenuStatus();
        this.isVisible = dto.getIsVisible();
    }

    public void updateMenu(MenuRequestDto dto) {
        this.menuName = dto.getMenuName();
        this.menuGroup = dto.getMenuGroup();
        this.menuPrice = dto.getMenuPrice();
        this.menuImage = dto.getMenuImage();
        this.menuDescription = dto.getMenuDescription();
        this.menuStatus = dto.getMenuStatus();
        this.isVisible = dto.getIsVisible();
    }

    public Menu(UUID companyId, String menuName, String menuGroup,
                Integer menuPrice, String menuImage, String menuDescription,
                MenuStatus menuStatus, Boolean isVisible) {
        this.companyId = companyId;
        this.menuName = menuName;
        this.menuGroup = menuGroup;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
        this.menuDescription = menuDescription;
        this.menuStatus = menuStatus;
        this.isVisible = isVisible;
    }
}
