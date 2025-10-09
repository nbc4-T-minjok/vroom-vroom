package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.service.BusinessHourService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BusinessHourController {

    private final BusinessHourService businessHourService;

    @PostMapping("/companies/{companyId}/business_hours")
    public BaseResponse createBusinessHour(@PathVariable UUID companyId, @RequestBody BusinessHourRequestDto requestDto) {
        businessHourService.createBusinessHour(companyId, requestDto);
        return new BaseResponse<>();
    }

}
