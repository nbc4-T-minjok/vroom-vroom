package com.sparta.vroomvroom.domain.order.model.entity;

import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order_menus")
@Getter
@NoArgsConstructor
public class OrderMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_menu_id")
    private UUID orderMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "menu_amount", nullable = false)
    private int menuAmount;

    @Column(name = "menu_price", nullable = false)
    private int menuPrice;

    public static OrderMenu createOrderMenu(
            Order order,
            Menu menu,
            Integer menuAmount,
            Integer menuPrice,
            String createdBy
    ) {
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.order = order;
        orderMenu.menu = menu;
        orderMenu.menuAmount = menuAmount;
        orderMenu.menuPrice = menuPrice;
        orderMenu.create(createdBy);
        return orderMenu;
    }
}
