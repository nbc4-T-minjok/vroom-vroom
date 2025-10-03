package com.sparta.vroomvroom.domain.review.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "review_images")
public class ReviewImage{
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "review_image_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "url", nullable = false, length = 255)
    private String url;
    
}
