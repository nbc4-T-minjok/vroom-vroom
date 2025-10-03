package com.sparta.vroomvroom.domain.review.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.user.model.entity.User;

import java.util.UUID;

public class ReviewResponseDto {
    private UUID id;
    private Order orderId;
    private User userId;
    private Company compId;
    private int rate;
    private String reviewContents;

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.orderId = review.getOrder();
        this.userId = review.getUser();
        this.compId = review.getCompany();
        this.rate = review.getRate();
        this.reviewContents = review.getContents();

    }
}
