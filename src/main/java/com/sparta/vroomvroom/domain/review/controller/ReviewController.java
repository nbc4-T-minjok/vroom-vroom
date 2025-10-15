package com.sparta.vroomvroom.domain.review.controller;

import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.request.ReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.response.ReviewResponseDto;
import com.sparta.vroomvroom.domain.review.service.ReviewService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "reviews", description = "리뷰 관련 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰작성_주문
    @Operation(summary = "리뷰작성_주문", description = "주문 정보를 가지고 리뷰를 작성합니다.",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "multipart/form-data",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.REVIEW_ORDER_CREATE_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_CUSTOMER"})
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
    @Operation(summary = "리뷰작성_업체", description = "리뷰에 대한 사장님 리뷰 작성합니다.",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.REVIEW_COMPANY_CREATE_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_OWNER"})
    @PostMapping("/companies/{compId}/reviews")
    public BaseResponse createReviewCompany(
            @Valid @RequestBody OwnerReviewRequestDto requestDto,
            @PathVariable UUID compId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID reviewId = requestDto.getReviewId();
        Long userId = userDetails.getUser().getUserId();
        reviewService.createReviewCompany(compId, reviewId, userId, requestDto);
        return new BaseResponse();
    }

    // 리뷰 목록 조회_고객
    @Operation(summary = "리뷰 목록 조회_고객", description = "고객 기준으로 작성한 모든 리뷰 검색")
    @GetMapping("/users/{userId}/reviews")
    public  BaseResponse getReviewsList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", defaultValue = "0") int page,         // 페이지 번호
            @RequestParam(value = "size", defaultValue = "10") int size,         // 조회할 항목수
            @RequestParam(value = "sort", defaultValue = "userId") String sort       // 정렬기준
    ){
        Long userId = userDetails.getUser().getUserId();
        List<ReviewResponseDto> result = reviewService.getReviewList(userId, page, size, sort);
        return new BaseResponse(result);
    }

    // 리뷰 목록 조회_업체
    @Operation(summary = "리뷰 목록 조회_업체", description = "업체 기준으로 작성한 모든 리뷰 검색")
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

    // 리뷰 수정_고객
    @Operation(summary = "리뷰 수정_고객", description = "고객 리뷰를 수정 합니다.",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "multipart/form-data",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.REVIEW_ORDER_UPDATE_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_MASTER","ROLE_MANAGER","ROLE_CUSTOMER"})
    @PatchMapping(value="/users/{userId}/reviews/{reviewId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    @Operation(summary = "리뷰 수정_업체", description = "사장님 리뷰를 수정 합니다.",
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.REVIEW_COMPANY_UPDATE_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_MASTER","ROLE_MANAGER","ROLE_OWNER"})
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
    @Operation(summary = "리뷰 삭제_고객", description = "고객의 리뷰를 삭제 합니다.")
    @Secured({"ROLE_MASTER","ROLE_MANAGER","ROLE_CUSTOMER"})
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
    @Operation(summary = "리뷰 삭제_업체", description = "사장님 리뷰를 삭제 합니다.")
    @Secured({"ROLE_MASTER","ROLE_MANAGER","ROLE_OWNER"})
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
