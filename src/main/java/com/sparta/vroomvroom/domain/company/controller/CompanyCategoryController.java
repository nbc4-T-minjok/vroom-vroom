package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryResponseDto;
import com.sparta.vroomvroom.domain.company.service.CompanyCategoryService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.constants.BaseResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyCategoryController {
    private final CompanyCategoryService companyCategoryService;

    @Secured("ROLE_MANAGER")
    @PostMapping("/company-categories")
    public BaseResponse createCompanyCategories(@Valid @RequestBody CompanyCategoryRequestDto requestDto) {
        companyCategoryService.createCompanyCategories(requestDto);
        return new BaseResponse();
    }

    @GetMapping("/company-categories")
    public BaseResponse getCompanyCategories(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Page<CompanyCategoryResponseDto> companyCategories = companyCategoryService.getCompanyCategories(page, size);
        return new BaseResponse(companyCategories);
    }

    @Secured("ROLE_MANAGER")
    @PatchMapping("/company-categories/{companyCategoryId}")
    public BaseResponse updateCompanyCategories(@PathVariable UUID companyCategoryId,
                                                @Valid @RequestBody CompanyCategoryRequestDto requestDto) {
        CompanyCategoryResponseDto responseDto = companyCategoryService.updateCompanyCategories(companyCategoryId, requestDto);
        return new BaseResponse(responseDto);
    }

    @Secured("ROLE_MANAGER")
    @DeleteMapping("/company-categories/{companyCategoryId}")
    public BaseResponse deleteCompanyCategories(@PathVariable UUID companyCategoryId) {
        companyCategoryService.deleteCompanyCategories(companyCategoryId);
        return new BaseResponse();
    }
}
