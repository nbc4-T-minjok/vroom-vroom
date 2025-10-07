package com.sparta.vroomvroom.domain.review.controller;

import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequsetDto;
import com.sparta.vroomvroom.domain.review.model.dto.request.ReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.response.ReviewResponseDto;
import com.sparta.vroomvroom.domain.review.service.ReviewService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
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
    @PostMapping("/review/{reviewId}/ownerReviews")
    public BaseResponse createReviewCompany(
            @Valid @RequestBody OwnerReviewRequsetDto requestDto,
            @PathVariable UUID reviewId,
            @CookieValue("userId") Long userId){
        reviewService.createReviewCompany(reviewId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 목록 조회_고객
    @GetMapping("/reviews/{userId}")
    public  BaseResponse getReviewsList(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,         // 페이지 번호
            @RequestParam(value = "size", defaultValue = "10") int size,         // 조회할 항목수
            @RequestParam(value = "sort", defaultValue = "userId") String sort       // 정렬기준
    ){
        List<ReviewResponseDto> result = reviewService.getReviewList(userId, page, size, sort);
        return new BaseResponse(result);
    }

    // 리뷰 목록 조회_업체
    @GetMapping("/{compId}/reviews")
    public  BaseResponse getReviewListCompany(
            @PathVariable UUID compId,
            @CookieValue("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,         // 페이지 번호
            @RequestParam(value = "size", defaultValue = "10") int size,         // 조회할 항목수
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort       // 정렬기준
    ){

        List<ReviewResponseDto> result = reviewService.getReviewListCompany(compId, page, size, sort);
        return new BaseResponse(result);
    }

    // 리뷰 상세 조회_고객
    @GetMapping("/reviews/{userId}/{reviewId}")
    public BaseResponse getReview(
            @PathVariable Long userId,
            @PathVariable UUID reviewId
    ){
        List<ReviewResponseDto> result = reviewService.getReview(userId, reviewId);
        return new BaseResponse(result);
    }


    // 리뷰 상세 조회_업체
    @GetMapping("/{compId}/reviews/{reviewId}")
    public BaseResponse getReviewCompany(
            @PathVariable UUID compId,
            @PathVariable UUID reviewId
    ){
        System.out.println("상세리뷰 조회 시작");
        List<ReviewResponseDto> result = reviewService.getReviewCompany(compId, reviewId);
        return new BaseResponse(result);
    }

    // 리뷰 수정_고객
//    @PatchMapping("/reviews/{reviewId}")

    // 리뷰 수정_업체
//    @PatchMapping("/reviews/{ownerReviewId}")

    // 리뷰 삭제_고객
//    @DeleteMapping("/reviews/{reviewId}")

    // 리뷰 삭제_업체
//    @DeleteMapping("/reviews/{ownerReviewId}")

}
