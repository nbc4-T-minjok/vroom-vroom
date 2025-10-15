package com.sparta.vroomvroom.domain.manager.controller;

import com.sparta.vroomvroom.domain.manager.model.dto.request.ManagerRegisterRequest;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerCompanyResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerOrderResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerReviewResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerUserResponse;
import com.sparta.vroomvroom.domain.manager.service.ManagerService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @Operation(summary = "MASTER용 MANAGER 생성 API",description = SwaggerDescription.MANAGER_REGISTER_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.MANAGER_REGISTER_REQUEST)
                            }
                    )
            ))
    @PostMapping("/v1/manager/register")
    @Secured({"ROLE_MASTER"})
    public BaseResponse createManager(
            @RequestBody ManagerRegisterRequest request
            ) {
        managerService.createManager(request);
        return new BaseResponse<>();
    }

    @Operation(summary = "관리자용 전체 주문 목록 조회 API")
    @GetMapping("/v1/manager/orders")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse<List<ManagerOrderResponse>> getOrders() {
        List<ManagerOrderResponse> response = managerService.getOrders();
        return new BaseResponse<>(response);
    }

    @Operation(summary = "관리자용 전체 사용자 목록 조회 API")
    @GetMapping("/v1/manager/users")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse<List<ManagerUserResponse>> getUsers() {
        List<ManagerUserResponse> response = managerService.getUsers();
        return new BaseResponse<>(response);
    }

    @Operation(summary = "관리자용 전체 업체 목록 조회 API")
    @GetMapping("/v1/manager/companies")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse<List<ManagerCompanyResponse>> getCompanies() {
        List<ManagerCompanyResponse> response = managerService.getCompanies();
        return new BaseResponse<>(response);
    }

    @Operation(summary = "관리자용 전체 리뷰 목록 조회 API")
    @GetMapping("/v1/manager/reviews")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse<List<ManagerReviewResponse>> getReviews() {
        List<ManagerReviewResponse> response = managerService.getReviews();
        return new BaseResponse<>(response);
    }

    @Operation(summary = "관리자용 주문 취소처리 API")
    @GetMapping("/v1/manager/orders/{orderId}")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse cancleOrder(@PathVariable UUID orderId) {
        managerService.cancleOrder(orderId);
        return new BaseResponse<>();
    }

    @Operation(summary = "관리자용 회원 삭제 API")
    @GetMapping("/v1/manager/users/{userId}")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse deleteUser(@PathVariable Long userId) {
        managerService.deleteUser(userId);
        return new BaseResponse<>();
    }

    @Operation(summary = "관리자용 업체 삭제 API")
    @GetMapping("/v1/manager/companies/{companyId}")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse deleteCompany(@PathVariable UUID companyId) {
        managerService.deleteCompany(companyId);
        return new BaseResponse<>();
    }

    @Operation(summary = "관리자용 리뷰 삭제 API")
    @GetMapping("/v1/manager/reviews/{reviewId}")
    @Secured({"ROLE_MANAGER","ROLE_MASTER"})
    public BaseResponse deleteReview(@PathVariable UUID reviewId) {
        managerService.deleteReview(reviewId);
        return new BaseResponse<>();
    }

}
