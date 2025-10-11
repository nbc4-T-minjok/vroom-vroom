package com.sparta.vroomvroom.domain.review.model.dto.response;

import com.sparta.vroomvroom.domain.review.model.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
    private List<String> imageUrls;

    public ReviewResponseDto(Review review){
        this.id = review.getId();
        this.orderId = review.getOrder().getOrderId();
        this.userId = review.getUserId().getUserId();
        this.compId = review.getCompany().getCompanyId();
        this.rate = review.getRate();
        this.reviewContents = review.getContents();
        this.ownerReviewContents = review.getOwnerReview() != null ? review.getOwnerReview().getContents() : null;
    }

    public ReviewResponseDto(Review review, List<String> imageUrls){
        this.id = review.getId();
        this.orderId = review.getOrder().getOrderId();
        this.userId = review.getUserId().getUserId();
        this.compId = review.getCompany().getCompanyId();
        this.rate = review.getRate();
        this.reviewContents = review.getContents();
        this.ownerReviewContents = review.getOwnerReview() != null ? review.getOwnerReview().getContents() : null;
        this.imageUrls = imageUrls;
    }

}
