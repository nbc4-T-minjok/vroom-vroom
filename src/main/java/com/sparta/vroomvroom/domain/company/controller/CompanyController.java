package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyListResponseDto;
import com.sparta.vroomvroom.domain.company.model.dto.request.CompanyRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyDetailResponseDto;
import com.sparta.vroomvroom.domain.company.service.CompanyService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;

import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 업체등록

    @Operation(summary = "업체등록 API", description = SwaggerDescription.COMPANY_CREATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "multipart/form-data",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.COMPANY_REGISTER_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PostMapping(value = "/company-categories/{companyCategoryId}/companies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse createCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable UUID companyCategoryId,
                                      @RequestPart("requestDto") @Valid CompanyRequestDto requestDto,
                                      @RequestPart(value = "logoFile", required = false) MultipartFile logoFile) {
        companyService.createCompany(userDetails.getUser().getUserId(), companyCategoryId, requestDto, logoFile);
        return new BaseResponse();
    }

    // 업체 상세 조회
    @Operation(summary = "업체 상세 조회 API", description = SwaggerDescription.COMPANY_DETAIL_REQUEST)
    @GetMapping("/companies/{companyId}")
    public BaseResponse getCompany(@PathVariable UUID companyId) {
        CompanyDetailResponseDto responseDto = companyService.getCompany(companyId);
        return new BaseResponse(responseDto);
    }

//    // 업체 목록 조회
//    @GetMapping("/companies")
//    public BaseResponse getCompanies() {
//        List<CompanyResponseDto> responseDtos = companyService.getCompanies();
//        return new BaseResponse(responseDtos);
//    }

    // 업체 카테고리별 목록 조회
    @Operation(summary = "업체 카테고리별 조회 API", description = SwaggerDescription.COMPANY_CATEGORY_SEARCH_REQUEST)
    @GetMapping("/company-categories/{companyCategoryId}/companies")
    public BaseResponse<CompanyListResponseDto> getCompaniesByCategory(@PathVariable UUID companyCategoryId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                               @RequestParam(name = "sortBy", required = false) String sortBy,
                                               @RequestParam(name = "isAsc") Boolean isAsc) {
        Long userId = userDetails.getUser().getUserId();
        CompanyListResponseDto response = companyService.getCompaniesByCategory(userId,page,size,sortBy,isAsc,companyCategoryId);
        return new BaseResponse(response);
    }

    // 업체 키워드 조회
    @Operation(summary = "업체 카테고리별 조회 API", description = SwaggerDescription.COMPANY_KEYWORD_SEARCH_REQUEST)
    @GetMapping("/companies/search")
    public BaseResponse<CompanyListResponseDto> searchByKeyword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "isAsc") Boolean isAsc) {
        if(keyword.length() < 2 || keyword.length() > 20) throw new IllegalArgumentException("검색어는 2글자 이상 20글자 이하여야 합니다.");
        Long userId = userDetails.getUser().getUserId();
        CompanyListResponseDto response = companyService.searchByKeyword(userId,keyword,page,size,sortBy,isAsc);
        return new BaseResponse(response);
    }

    // 업체 수정
    @Operation(summary = "업체 수정 API", description = SwaggerDescription.COMPANY_CREATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "multipart/form",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.COMPANY_CREATE_REQUEST)
                            }
                    )
            ))
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PatchMapping("/companies/{companyId}")
    public BaseResponse updateCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable UUID companyId,
                                      @RequestPart("companyRequestDto") @Valid CompanyRequestDto requestDto,
                                      @RequestPart(value = "logoFile", required = false) MultipartFile logoFile) {
        CompanyDetailResponseDto responseDto = companyService.updateCompany(userDetails.getUser().getUserId(), companyId, requestDto, logoFile);
        return new BaseResponse(responseDto);
    }

    // 업체 삭제
    @Operation(summary = "업체 삭제 API", description = SwaggerDescription.COMPANY_DELETE_REQUEST)
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @DeleteMapping("/companies/{companyId}")
    public BaseResponse deleteCompany(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID companyId) {
        companyService.deleteCompany(userDetails.getUser().getUserId(), companyId);
        return new BaseResponse();
    }
}
