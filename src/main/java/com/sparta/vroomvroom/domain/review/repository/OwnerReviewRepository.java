package com.sparta.vroomvroom.domain.review.repository;

import com.sparta.vroomvroom.domain.review.model.entity.OwnerReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OwnerReviewRepository extends JpaRepository<OwnerReview, UUID> {
}
