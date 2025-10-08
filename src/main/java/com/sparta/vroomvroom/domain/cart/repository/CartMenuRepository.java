package com.sparta.vroomvroom.domain.cart.repository;

import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartMenuRepository extends JpaRepository<CartMenu, UUID> {

    List<CartMenu> findByCart_CartId(UUID cartId);

    Optional<CartMenu> findByCart_CartIdAndMenu_MenuId(UUID cartId, UUID menuId);

    boolean existsByCart_CartId(UUID cartId);
}