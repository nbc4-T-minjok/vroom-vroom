package com.sparta.vroomvroom.domain.manager.controller;

import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerCompanyResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerOrderResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerReviewResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerUserResponse;
import com.sparta.vroomvroom.domain.manager.service.ManagerService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;


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

}
