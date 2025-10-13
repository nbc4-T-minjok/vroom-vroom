package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.model.dto.request.SpecialBusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.SpecialBusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.service.SpecialBusinessHourService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
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

    @Operation(summary = "특별영업시간 생성 API", description = SwaggerDescription.SPECIAL_BUSINESS_HOUR_CREATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.SPECIAL_BUSINESS_HOUR_CREATE_REQUEST)
                            }
                    )
            ))
    @PostMapping("/companies/{companyId}/special_business_hours")
    public BaseResponse createSpecialBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable UUID companyId,
                                                  @Valid @RequestBody SpecialBusinessHourRequestDto requestDto) {
        specialBusinessHourService.createSpecialBusinessHour(userDetails.getUser(), companyId, requestDto);
        return new BaseResponse();
    }

    @Operation(summary = "특별영업시간 목록 조회 API", description = SwaggerDescription.SPECIAL_BUSINESS_HOUR_CREATE_REQUEST)
            @GetMapping("/companies/{companyId}/special_business_hours")
    public BaseResponse getSpecialBusinessHours(@PathVariable UUID companyId) {
        List<SpecialBusinessHourResponseDto> responseDtos = specialBusinessHourService.getSpecialBusinessHours(companyId);
        return new BaseResponse(responseDtos);
    }

    @Operation(summary = "특별영업시간 수정 API", description = SwaggerDescription.SPECIAL_BUSINESS_HOUR_UPDATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.SPECIAL_BUSINESS_HOUR_UPDATE_REQUEST)
                            }
                    )
            ))
    @PatchMapping("/companies/{companyId}/special_business_hours/{specialBusinessHourId}")
    public BaseResponse updateSpecialBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable UUID companyId, @PathVariable UUID specialBusinessHourId,
                                                   @Valid @RequestBody SpecialBusinessHourRequestDto requestDto) {
        SpecialBusinessHourResponseDto responseDto = specialBusinessHourService.updateSpecialBusinessHour(userDetails.getUser(), companyId, specialBusinessHourId, requestDto);
        return new BaseResponse(responseDto);
    }

    @Operation(summary = "특별영업시간 삭제 API", description = SwaggerDescription.SPECIAL_BUSINESS_HOUR_UPDATE_REQUEST)
    @DeleteMapping("/companies/{companyId}/special_business_hours/{specialBusinessHourId}")
    public BaseResponse deleteSpecialBusinessHour(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @PathVariable UUID companyId, @PathVariable UUID specialBusinessHourId){
        specialBusinessHourService.deleteSpecialBusinessHour(userDetails.getUser(), companyId, specialBusinessHourId);
        return new BaseResponse<>();
    }
}

