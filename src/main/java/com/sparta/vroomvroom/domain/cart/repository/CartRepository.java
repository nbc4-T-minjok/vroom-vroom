package com.sparta.vroomvroom.domain.cart.repository;

import com.sparta.vroomvroom.domain.cart.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}
