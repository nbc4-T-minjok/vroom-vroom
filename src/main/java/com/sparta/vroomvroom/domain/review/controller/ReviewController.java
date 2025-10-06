package com.sparta.vroomvroom.domain.review.controller;

import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequsetDto;
import com.sparta.vroomvroom.domain.review.model.dto.request.ReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.response.ReviewResponseDto;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.review.repository.OwnerReviewRepository;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import com.sparta.vroomvroom.domain.review.service.ReviewService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰작성
    @PostMapping("/{orderId}/reviews")
    public BaseResponse createReview(
            @Valid @RequestBody ReviewRequestDto requestDto,
            @PathVariable UUID orderId,
            @CookieValue("userId") Long userId){
        reviewService.createReview(orderId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰작성_업체
    @PostMapping("/review/{compId}/ownerReviews")
    public BaseResponse createReviewCompany(
            @Valid @RequestBody OwnerReviewRequsetDto requestDto,
            @PathVariable UUID reviewId,
            @CookieValue("userId") Long userId){
        reviewService.createReviewCompany(reviewId, requestDto);
        return new BaseResponse();
    }


    // 리뷰 목록 조회_고객
//    @GetMapping("/reviews/{userId}")

    // 리뷰 목록 조회_업체
//    @GetMapping("/{compId}/reviews")

    // 리뷰 상세 조회_고객
//    @GetMapping("/reviews/{userId}/{reviewId}")

    // 리뷰 상세 조회_업체
//    @GetMapping("/{compId}/reviews/{reviewId}")

    // 리뷰 수정_고객
//    @PatchMapping("/reviews/{reviewId}")

    // 리뷰 수정_업체
//    @PatchMapping("/reviews/{ownerReviewId}")

    // 리뷰 삭제_고객
//    @DeleteMapping("/reviews/{reviewId}")

    // 리뷰 삭제_업체
//    @DeleteMapping("/reviews/{ownerReviewId}")

}
