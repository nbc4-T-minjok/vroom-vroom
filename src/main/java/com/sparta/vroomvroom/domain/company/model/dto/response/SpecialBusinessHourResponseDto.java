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
    LocalDate date;
    LocalTime openedAt;
    LocalTime closedAt;
    BusinessStatus businessStatus;


    public static SpecialBusinessHourResponseDto of(SpecialBusinessHour specialBusinessHour) {
        return SpecialBusinessHourResponseDto.builder()
                .date(specialBusinessHour.getDate())
                .openedAt(specialBusinessHour.getOpenedAt())
                .closedAt(specialBusinessHour.getClosedAt())
                .businessStatus(specialBusinessHour.getBusinessStatus())
                .build();
    }
}
