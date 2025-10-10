package com.sparta.vroomvroom.domain.cart.model.entity;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id")
    private UUID cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_empty", nullable = false)
    private boolean isEmpty = true;

    // 생성 메서드
    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.user = user;
        cart.isEmpty = true;
        cart.create(user.getUserName());
        return cart;
    }

    // 비즈니스 로직 메서드
    public void markAsNotEmpty() {
        this.isEmpty = false;
    }

    public void markAsEmpty() {
        this.isEmpty = true;
    }

    public void updateEmptyStatus(boolean hasItems) {
        this.isEmpty = !hasItems;
    }

}
