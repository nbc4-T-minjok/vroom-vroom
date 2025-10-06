package com.sparta.vroomvroom.domain.review.model.entity;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "review_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_id", nullable = false)
    private Company company;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "review_contents", nullable = false, columnDefinition = "text")
    private String contents;



}
