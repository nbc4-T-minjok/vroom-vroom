package com.sparta.vroomvroom.domain.menu.model.entity;

import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.order.model.entity.OrderMenu;
import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menus")
@Getter
@Setter
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID menuId;

    @Column(name = "company_id", nullable = false, columnDefinition = "uuid")
    private UUID companyId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

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
    private Instant createdAt = Instant.now();

    @Column(name = "created_by", nullable = false, length = 20)
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "delete_by", length = 20)
    private String deletedBy;


    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartMenu> cartMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();


}
