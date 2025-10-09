package com.sparta.vroomvroom.domain.company.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.service.BusinessHourService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BusinessHourController {

    private final BusinessHourService businessHourService;

    // 영업시간 등록
    @PostMapping("/companies/{companyId}/business_hours")
    public BaseResponse createBusinessHour(@PathVariable UUID companyId, @RequestBody BusinessHourRequestDto requestDto) {
        businessHourService.createBusinessHour(companyId, requestDto);
        return new BaseResponse<>();
    }

    // 영업시간 조회
    @GetMapping("/companies/{companyId}/business_hours")
    public BaseResponse getBusinessHour(@PathVariable UUID companyId) {
        List<BusinessHourResponseDto> result =  businessHourService.getBusinessHour(companyId);
        return new BaseResponse(result);
    }

    // 영업시간 수정
    @PatchMapping("/companies/{companyId}/business_hours/{businessHourId}")
    public BaseResponse patchBusinessHour(@PathVariable UUID companyId, @PathVariable UUID businessHourId,
                                            @RequestBody BusinessHourRequestDto requestDto) {
        businessHourService.patchBusinessHour(companyId, businessHourId, requestDto);
        return new BaseResponse();
    }

    // 영업시간 삭제
    @DeleteMapping("/companies/{companyId}/business_hours/{businessHourId}")
    public BaseResponse deleteBusinessHour(@PathVariable UUID companyId, @PathVariable UUID businessHourId) {
        businessHourService.deleteBusinessHour(companyId, businessHourId);
        return new BaseResponse();
    }

}
