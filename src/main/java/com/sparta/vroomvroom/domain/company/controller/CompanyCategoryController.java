package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryRequestDto;
import com.sparta.vroomvroom.domain.company.service.CompanyCategoryService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.constants.BaseResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyCategoryController {
    private final CompanyCategoryService companyCategoryService;

    @PostMapping("/company-categories")
    public BaseResponse createCompanyCategories(@Valid @RequestBody CompanyCategoryRequestDto requestDto) {
        companyCategoryService.createCompanyCategories(requestDto);
        return new BaseResponse();
    }
}
