package com.sparta.vroomvroom.domain.review.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewResponseDto {
    private UUID id;
    private UUID orderId;
    private Long userId;
    private UUID compId;
    private int rate;
    private String reviewContents;
    private String ownerReviewContents;

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.orderId = review.getOrder().getOrderId();
        this.userId = review.getUserId().getUserId();
        this.compId = review.getCompany().getCompanyId();
        this.rate = review.getRate();
        this.reviewContents = review.getContents();
        this.ownerReviewContents = review.getOwnerReview() != null ? review.getOwnerReview().getContents() : null;

    }

}
