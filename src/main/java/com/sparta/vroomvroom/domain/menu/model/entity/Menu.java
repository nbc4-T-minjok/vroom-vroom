package com.sparta.vroomvroom.domain.menu.model.entity;

import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
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
    @Column(name = "menu_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID menuId;

    @Column(name = "company_id", nullable = false, columnDefinition = "uuid")
    private UUID companyId;

    @Column(name = "name", nullable = false, length = 50)
    private String menuName;

    @Column(name = "price", nullable = false)
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
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by", length = 20)
    private String deletedBy;


    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartMenu> cartMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();


    @Builder
    public Menu(Company company, String menuName, String menuGroup, Integer menuPrice,
                String menuImage, String menuDescription, MenuStatus menuStatus, Boolean isVisible) {
        this.companyId = company.getCompanyId();
        this.menuName = menuName;
        this.menuGroup = menuGroup;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
        this.menuDescription = menuDescription;
        this.menuStatus = menuStatus;
        this.isVisible = isVisible;
    }

    public void updateMenu(String menuName, String menuGroup, Integer menuPrice,
                           String menuImage, String menuDescription,
                           MenuStatus menuStatus, Boolean isVisible) {
        this.menuName = menuName;
        this.menuGroup = menuGroup;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
        this.menuDescription = menuDescription;
        this.menuStatus = menuStatus;
        this.isVisible = isVisible;
    }
}
