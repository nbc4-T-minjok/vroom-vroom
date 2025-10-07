package com.sparta.vroomvroom.domain.review.repository;

import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Optional<Review> findByOrder(Order order);

    Page<Review> findByCompany_CompanyId(UUID compId, Pageable pageable);

    Page<Review> findByUserId_UserId(Long userId, Pageable pageable);

    List<Review> findByUserId_UserIdAndId(Long userId, UUID reviewId);

    List<Review> findByCompany_CompanyIdAndId(UUID compId, UUID reviewId);
}
