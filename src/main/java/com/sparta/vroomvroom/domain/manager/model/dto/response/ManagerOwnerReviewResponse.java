package com.sparta.vroomvroom.domain.manager.model.dto.response;

import com.sparta.vroomvroom.domain.review.model.entity.OwnerReview;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ManagerOwnerReviewResponse {

    private UUID ownerReviewId;
    private String contents;

    public ManagerOwnerReviewResponse(OwnerReview ownerReview) {
        this.ownerReviewId = ownerReview.getId();
        this.contents = ownerReview.getContents();
    }
}
