package com.sparta.vroomvroom.domain.company.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.service.BusinessHourService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.constants.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.constants.SwaggerExamples;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BusinessHourController {

    private final BusinessHourService businessHourService;

    // 영업시간 등록
    @Operation(summary = "영업시간 등록 API", description = SwaggerDescription.BUSINESS_HOUR_CREATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.BUSINESS_HOUR_CREATE_REQUEST)
                            }
                    )
            ))
    @Secured({"ROLE_MANAGER","ROLE_OWNER"})
    @PostMapping("/companies/{companyId}/business_hours")
    public BaseResponse createBusinessHour(@PathVariable UUID companyId, @RequestBody BusinessHourRequestDto requestDto) {
        businessHourService.createBusinessHour(companyId, requestDto);
        return new BaseResponse<>();
    }

    // 영업시간 조회
    @Operation(summary = "영업시간 조회 API")
    @Secured({"ROLE_MANAGER","ROLE_OWNER"})
    @GetMapping("/companies/{companyId}/business_hours")
    public BaseResponse getBusinessHour(@PathVariable UUID companyId) {
        List<BusinessHourResponseDto> result =  businessHourService.getBusinessHour(companyId);
        return new BaseResponse(result);
    }

    // 영업시간 수정
    @Operation(summary = "영업시간 수정 API", description = SwaggerDescription.BUSINESS_HOUR_UPDATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.BUSINESS_HOUR_UPDATE_REQUEST)
                            }
                    )
            ))
    @Secured({"ROLE_MANAGER","ROLE_OWNER"})
    @PatchMapping("/companies/{companyId}/business_hours/{businessHourId}")
    public BaseResponse updateBusinessHour(@PathVariable UUID companyId, @PathVariable UUID businessHourId,
                                            @RequestBody BusinessHourRequestDto requestDto) {
        businessHourService.updateBusinessHour(companyId, businessHourId, requestDto);
        return new BaseResponse();
    }

    // 영업시간 삭제
    @Operation(summary = "영업시간 삭제 API")
    @Secured({"ROLE_MANAGER","ROLE_OWNER"})
    @DeleteMapping("/companies/{companyId}/business_hours/{businessHourId}")
    public BaseResponse deleteBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID companyId, @PathVariable UUID businessHourId) {
        String userName = userDetails.getUser().getUserName();
        businessHourService.deleteBusinessHour(companyId, businessHourId, userName);
        return new BaseResponse();
    }

}
