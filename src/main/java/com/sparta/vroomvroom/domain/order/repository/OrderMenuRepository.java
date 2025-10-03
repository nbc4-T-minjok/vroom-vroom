package com.sparta.vroomvroom.domain.order.repository;

import com.sparta.vroomvroom.domain.order.model.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, UUID> {
}
