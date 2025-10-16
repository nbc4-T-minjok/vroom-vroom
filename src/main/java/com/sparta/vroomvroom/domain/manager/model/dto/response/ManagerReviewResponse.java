package com.sparta.vroomvroom.domain.manager.model.dto.response;

import com.sparta.vroomvroom.domain.review.model.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ManagerReviewResponse {
    private UUID reviewId;
    private String contents;
    private ManagerOwnerReviewResponse ownerReview;

    public ManagerReviewResponse(Review review) {
        this.reviewId = review.getId();
        this.contents = review.getContents();
        this.ownerReview = new ManagerOwnerReviewResponse(review.getOwnerReview());
    }
}
