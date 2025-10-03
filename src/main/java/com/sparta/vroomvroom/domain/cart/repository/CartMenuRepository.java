package com.sparta.vroomvroom.domain.cart.repository;

import com.sparta.vroomvroom.domain.cart.model.entity.Cart;
import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartMenuRepository extends JpaRepository<CartMenu, UUID> {
}
