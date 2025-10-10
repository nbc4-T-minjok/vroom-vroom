package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.request.SpecialBusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.SpecialBusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.service.SpecialBusinessHourService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Secured({"ROLE_OWNER", "ROLE_MANAGER"})
public class SpecialBusinessHourController {
    private final SpecialBusinessHourService specialBusinessHourService;

    @PostMapping("/companies/{companyId}/special_business_hours")
    public BaseResponse createSpecialBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable UUID companyId,
                                                  @RequestBody SpecialBusinessHourRequestDto requestDto) {
        specialBusinessHourService.createSpecialBusinessHour(userDetails.getUser().getUserId(), companyId, requestDto);
        return new BaseResponse();
    }

    @GetMapping("/companies/{companyId}/special_business_hours")
    public BaseResponse getSpecialBusinessHours(@PathVariable UUID companyId) {
        List<SpecialBusinessHourResponseDto> responseDtos = specialBusinessHourService.getSpecialBusinessHours(companyId);
        return new BaseResponse(responseDtos);
    }

    @PatchMapping("/companies/{companyId}/special_business_hours/{specialBusinessHourId}")
    public BaseResponse updateSpecialBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable UUID companyId, @PathVariable UUID specialBusinessHourId,
                                                   @RequestBody SpecialBusinessHourRequestDto requestDto) {
        SpecialBusinessHourResponseDto responseDto = specialBusinessHourService.updateSpecialBusinessHour(userDetails.getUser().getUserId(), companyId, specialBusinessHourId, requestDto);
        return new BaseResponse(responseDto);
    }

    @DeleteMapping("/companies/{companyId}/special_business_hours/{specialBusinessHourId}")
    public BaseResponse deleteSpecialBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable UUID companyId, @PathVariable UUID specialBusinessHourId){
        specialBusinessHourService.deleteSpecialBusinessHour(userDetails.getUser().getUserId(), companyId, specialBusinessHourId);
        return new BaseResponse<>();
    }
}

