package com.sparta.vroomvroom.domain.company.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import com.sparta.vroomvroom.global.conmon.constants.BusinessStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class SpecialBusinessHourResponseDto {

    private LocalDate date;                 // 특별 영업날짜
    private LocalTime openedAt;             // 오픈시간
    private LocalTime closedAt;             // 마감시간
    private BusinessStatus businessStatus;  // 영업상태

    // Entity -> Dto 변환 메서드
    public static SpecialBusinessHourResponseDto of(SpecialBusinessHour specialBusinessHour) {
        return SpecialBusinessHourResponseDto.builder()
                .date(specialBusinessHour.getDate())
                .openedAt(specialBusinessHour.getOpenedAt())
                .closedAt(specialBusinessHour.getClosedAt())
                .businessStatus(specialBusinessHour.getBusinessStatus())
                .build();

    }
}
