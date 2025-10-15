package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryResponseDto;
import com.sparta.vroomvroom.domain.company.service.CompanyCategoryService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.constants.BaseResponseStatus;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

    @Operation(summary = "카테고리 생성 API", description = SwaggerDescription.COMPANY_CATEGORY_CREATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.COMPANY_CATEGORY_CREATE_REQUEST)
                            }
                    )
            ))
    @Secured("ROLE_MANAGER")
    @PostMapping("/company-categories")
    public BaseResponse createCompanyCategories(@Valid @RequestBody CompanyCategoryRequestDto requestDto) {
        companyCategoryService.createCompanyCategories(requestDto);
        return new BaseResponse();
    }

    @Operation(summary = "카테고리 목록 조회 API")
    @GetMapping("/company-categories")
    public BaseResponse getCompanyCategories(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Page<CompanyCategoryResponseDto> companyCategories = companyCategoryService.getCompanyCategories(page, size);
        return new BaseResponse(companyCategories);
    }

    @Operation(summary = "카테고리 수정 API", description = SwaggerDescription.COMPANY_CATEGORY_UPDATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.COMPANY_CATEGORY_UPDATE_REQUEST)
                            }
                    )
            ))
    @Secured("ROLE_MANAGER")
    @PatchMapping("/company-categories/{companyCategoryId}")
    public BaseResponse updateCompanyCategories(@PathVariable UUID companyCategoryId,
                                                @Valid @RequestBody CompanyCategoryRequestDto requestDto) {
        CompanyCategoryResponseDto responseDto = companyCategoryService.updateCompanyCategories(companyCategoryId, requestDto);
        return new BaseResponse(responseDto);
    }

    @Operation(summary = "카테고리 삭제 API", description = SwaggerDescription.COMPANY_CATEGORY_UPDATE_REQUEST)
    @Secured("ROLE_MANAGER")
    @DeleteMapping("/company-categories/{companyCategoryId}")
    public BaseResponse deleteCompanyCategories(@PathVariable UUID companyCategoryId) {
        companyCategoryService.deleteCompanyCategories(companyCategoryId);
        return new BaseResponse();
    }
}
