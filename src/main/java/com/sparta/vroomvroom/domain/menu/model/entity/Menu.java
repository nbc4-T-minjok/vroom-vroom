package com.sparta.vroomvroom.domain.menu.model.entity;

import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.order.model.entity.OrderMenu;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartMenu> cartMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

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


    public void updateMenu(MenuRequestDto dto) {
        if (dto.getMenuName() != null) this.menuName = dto.getMenuName();
        if (dto.getMenuGroup() != null) this.menuGroup = dto.getMenuGroup();
        if (dto.getMenuPrice() != null) this.menuPrice = dto.getMenuPrice();
        if (dto.getMenuImage() != null) this.menuImage = dto.getMenuImage();
        if (dto.getMenuDescription() != null) this.menuDescription = dto.getMenuDescription();
        if (dto.getMenuStatus() != null) this.menuStatus = dto.getMenuStatus();
        if (dto.getIsVisible() != null) this.isVisible = dto.getIsVisible();
    }

    public Menu(Company company, String menuName, String menuGroup,
                Integer menuPrice, String menuImage, String menuDescription,
                MenuStatus menuStatus, Boolean isVisible) {
        this.company = company;
        this.menuName = menuName;
        this.menuGroup = menuGroup;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
        this.menuDescription = menuDescription;
        this.menuStatus = menuStatus;
        this.isVisible = isVisible;
    }
}
