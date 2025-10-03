package com.sparta.vroomvroom.domain.cart.model.entity;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Setter
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

}
