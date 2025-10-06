package com.sparta.vroomvroom.domain.review.repository;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Optional<Review> findByOrder(Order order);
}
