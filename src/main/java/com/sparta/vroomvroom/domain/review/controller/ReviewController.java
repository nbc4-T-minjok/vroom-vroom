package com.sparta.vroomvroom.domain.review.controller;

import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequestDto;
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

    // 리뷰작성_주문
    @PostMapping("/order/{orderId}/reviews")
    public BaseResponse createReview(
            @Valid @RequestBody ReviewRequestDto requestDto,
            @PathVariable UUID orderId,
            @CookieValue("userId") Long userId){
        reviewService.createReview(orderId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰작성_고객
    @PostMapping("/users/{userId}/reviews")
    public BaseResponse createReviewUser(
            @Valid @RequestBody ReviewRequestDto requestDto,
            @PathVariable Long userId){
        UUID orderId = requestDto.getOrderId();

        reviewService.createReview(orderId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰작성_업체
    @PostMapping("/companies/{compId}/reviews")
    public BaseResponse createReviewCompany(
            @Valid @RequestBody OwnerReviewRequestDto requestDto,
            @PathVariable UUID compId,
            @CookieValue("userId") Long userId){
        UUID reviewId = requestDto.getReviewId();
        reviewService.createReviewCompany(compId, reviewId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 목록 조회_고객
    @GetMapping("/users/{userId}/reviews")
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
    @GetMapping("/companies/{compId}/reviews")
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
    @GetMapping("/users/{userId}/reviews/{reviewId}")
    public BaseResponse getReview(
            @PathVariable Long userId,
            @PathVariable UUID reviewId
    ){
        List<ReviewResponseDto> result = reviewService.getReview(userId, reviewId);
        return new BaseResponse(result);
    }


    // 리뷰 상세 조회_업체
    @GetMapping("/companies/{compId}/reviews/{reviewId}")
    public BaseResponse getReviewCompany(
            @PathVariable UUID compId,
            @PathVariable UUID reviewId
    ){
        List<ReviewResponseDto> result = reviewService.getReviewCompany(compId, reviewId);
        return new BaseResponse(result);
    }

    // 리뷰 수정_고객
    @PatchMapping("/users/{userId}/reviews/{reviewId}")
    public BaseResponse updateReview(
            @PathVariable UUID reviewId,
            @CookieValue("userId") Long userId,
            @Valid @RequestBody ReviewRequestDto requestDto
    ){
        reviewService.updateReview(reviewId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 수정_업체
    @PatchMapping("/companies/{compId}/reviews/{reviewId}")
    public BaseResponse updateReviewCompany(
            @PathVariable UUID compId,
            @PathVariable UUID reviewId,
            @CookieValue("userId") Long userId,
            @Valid @RequestBody OwnerReviewRequestDto requestDto
    ){
        reviewService.updateReviewCompany(compId, reviewId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 삭제_고객
    @DeleteMapping("/users/{userId}/reviews/{reviewId}")
    public BaseResponse deleteReview(
            @PathVariable UUID reviewId,
            @CookieValue("userId") Long userId
    ){
        reviewService.deleteReview(reviewId, userId);
        return new BaseResponse();
    }

    // 리뷰 삭제_업체
    @DeleteMapping("/companies/{compId}/reviews/{reviewId}")
    public BaseResponse deleteReviewCompany(
            @PathVariable UUID reviewId,
            @CookieValue("userId") Long userId
    ){
        reviewService.deleteReviewCompany(reviewId, userId);
        return new BaseResponse();
    }

}
