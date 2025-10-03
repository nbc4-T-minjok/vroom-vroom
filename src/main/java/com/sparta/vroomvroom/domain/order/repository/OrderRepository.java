package com.sparta.vroomvroom.domain.order.repository;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
