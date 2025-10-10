package com.sparta.vroomvroom.domain.cart.model.entity;

import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cart_menus")
@Getter
@NoArgsConstructor
public class CartMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_menu_id")
    private UUID cartMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(name = "menu_amount", nullable = false)
    private int menuAmount;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by",updatable = false, nullable = false, length = 20)
    private String createdBy;

    // 생성 메서드
    public static CartMenu createCartMenu(Cart cart, Menu menu, int menuAmount, String userName) {
        CartMenu cartMenu = new CartMenu();
        cartMenu.cart = cart;
        cartMenu.menu = menu;
        cartMenu.menuAmount = menuAmount;
        cartMenu.createdAt = LocalDateTime.now();
        cartMenu.createdBy = userName;
        return cartMenu;
    }

    public void increaseAmount(int amount) {
        this.menuAmount += amount;
    }

    public void updateAmount(int newAmount) {
        if (newAmount < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
        }
        this.menuAmount = newAmount;
    }

    public boolean isSameCart(UUID cartId) {
        return this.cart.getCartId().equals(cartId);
    }
}
