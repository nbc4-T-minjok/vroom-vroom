package com.sparta.vroomvroom.domain.review.repository;

import com.sparta.vroomvroom.domain.review.model.entity.OwnerReview;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OwnerReviewRepository extends JpaRepository<OwnerReview, UUID> {
    Optional<OwnerReview> findByReviewId(UUID reviewId);
}
