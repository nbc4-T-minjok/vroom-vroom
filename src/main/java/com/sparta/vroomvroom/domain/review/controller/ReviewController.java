package com.sparta.vroomvroom.domain.review.controller;

import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.request.ReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.response.ReviewResponseDto;
import com.sparta.vroomvroom.domain.review.service.ReviewService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰작성_주문
    @PostMapping(value = "/order/{orderId}/reviews",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse createReview(
            @RequestPart("review") @Valid ReviewRequestDto requestDto,      // JSON DTO
            @RequestPart(value = "images", required = false) List<MultipartFile> images,    // 이미지 파일
            @PathVariable UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Long userId = userDetails.getUser().getUserId();
        reviewService.createReview(orderId, userId , requestDto, images);
        return new BaseResponse();
    }

    // 리뷰작성_업체
    @PostMapping("/companies/{compId}/reviews")
    public BaseResponse createReviewCompany(
            @Valid @RequestBody OwnerReviewRequestDto requestDto,
            @PathVariable UUID compId
    ){
        UUID reviewId = requestDto.getReviewId();
        reviewService.createReviewCompany(compId, reviewId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 목록 조회_고객
    // 고객 기준으로 작성한 모든 리뷰 검색
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
    // 업체 기준으로 모든 리뷰 검색
    @GetMapping("/companies/{compId}/reviews")
    public  BaseResponse getReviewListCompany(
            @PathVariable UUID compId,
            @RequestParam(value = "page", defaultValue = "0") int page,         // 페이지 번호
            @RequestParam(value = "size", defaultValue = "10") int size,         // 조회할 항목수
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort       // 정렬기준
    ){

        List<ReviewResponseDto> result = reviewService.getReviewListCompany(compId, page, size, sort);
        return new BaseResponse(result);
    }

    // 리뷰 상세 조회_고객
    // 리뷰 이미지는 상세 조회에서만 나옴
    @GetMapping("/users/{userId}/reviews/{reviewId}")
    public BaseResponse getReview(
            @PathVariable Long userId,
            @PathVariable UUID reviewId
    ){
        List<ReviewResponseDto> result = reviewService.getReview(userId, reviewId);
        return new BaseResponse(result);
    }


    // 리뷰 상세 조회_업체
    // 업체기준으로 리뷰 조회 후 상세 리뷰 확인할 때 사용
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
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart("review") @Valid ReviewRequestDto requestDto,                      // JSON DTO
            @RequestPart(value = "images", required = false) List<MultipartFile> images     // 이미지 파일
    ){
        Long userId = userDetails.getUser().getUserId();
        reviewService.updateReview(reviewId, userId, requestDto, images);
        return new BaseResponse();
    }

    // 리뷰 수정_업체
    @PatchMapping("/companies/{compId}/reviews/{reviewId}")
    public BaseResponse updateReviewCompany(
            @PathVariable UUID compId,
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody OwnerReviewRequestDto requestDto
    ){
        Long userId = userDetails.getUser().getUserId();
        reviewService.updateReviewCompany(compId, reviewId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 삭제_고객
    @DeleteMapping("/users/{userId}/reviews/{reviewId}")
    public BaseResponse deleteReview(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Long userId = userDetails.getUser().getUserId();
        reviewService.deleteReview(reviewId, userId);
        return new BaseResponse();
    }

    // 리뷰 삭제_업체
    @DeleteMapping("/companies/{compId}/reviews/{reviewId}")
    public BaseResponse deleteReviewCompany(
            @PathVariable UUID reviewId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Long userId = userDetails.getUser().getUserId();
        reviewService.deleteReviewCompany(reviewId, userId);
        return new BaseResponse();
    }

}
