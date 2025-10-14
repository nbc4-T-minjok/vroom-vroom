package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyListResponseDto;
import com.sparta.vroomvroom.domain.company.model.dto.request.CompanyRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyDetailResponseDto;
import com.sparta.vroomvroom.domain.company.service.CompanyService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    // 업체등록
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PostMapping("/company-categories/{companyCategoryId}/companies")
    public BaseResponse createCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable UUID companyCategoryId,
                                      @Valid @RequestBody CompanyRequestDto requestDto) {
        companyService.createCompany(userDetails.getUser().getUserId(), companyCategoryId, requestDto);
        return new BaseResponse();
    }

    // 업체 상세 조회
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
    @GetMapping("/company-categories/{companyCategoryId}/companies")
    public BaseResponse<CompanyListResponseDto> getCompaniesByCategory(@PathVariable UUID companyCategoryId,
                                               @RequestParam(name = "page", defaultValue = "1") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                               @RequestParam(name = "sortBy", required = false) String sortBy,
                                               @RequestParam(name = "isAsc") boolean isAsc) {
        CompanyListResponseDto response = companyService.getCompaniesByCategory(page,size,sortBy,isAsc,companyCategoryId);
        return new BaseResponse(response);
    }

    @GetMapping("/companies/search")
    public BaseResponse<CompanyListResponseDto> searchByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "isAsc") Boolean isAsc) {
        if(keyword.length() < 2 || keyword.length() > 20) throw new IllegalArgumentException("검색어는 2글자 이상 20글자 이하여야 합니다.");
        CompanyListResponseDto response = companyService.searchByKeyword(keyword,page,size,sortBy,isAsc);
        return new BaseResponse(response);
    }

    // 업체 수정
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PatchMapping("/companies/{companyId}")
    public BaseResponse updateCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable UUID companyId,
                                      @RequestBody CompanyRequestDto requestDto) {
        CompanyDetailResponseDto responseDto = companyService.updateCompany(userDetails.getUser().getUserId(), companyId, requestDto);
        return new BaseResponse(responseDto);
    }

    // 업체 삭제
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @DeleteMapping("/companies/{companyId}")
    public BaseResponse deleteCompany(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID companyId) {
        companyService.deleteCompany(userDetails.getUser().getUserId(), companyId);
        return new BaseResponse();
    }
}
