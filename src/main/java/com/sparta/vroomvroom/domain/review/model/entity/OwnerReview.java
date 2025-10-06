package com.sparta.vroomvroom.domain.review.model.entity;

import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "owner_reviews")
public class OwnerReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "owner_review_id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = true)
    private Review review;

    @Column(name = "owner_review_contents", nullable = false, columnDefinition = "text")
    private String contents;
}

