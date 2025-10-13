package com.sparta.vroomvroom.domain.company.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class BusinessHourResponseDto {
    private WeekDay weekDay;
    private LocalTime openedAt;
    private LocalTime closedAt;

    @JsonProperty("isClosed")
    boolean closed;

    public static BusinessHourResponseDto of(BusinessHour businessHour) {
        return BusinessHourResponseDto.builder()
                .weekDay(businessHour.getDay())
                .closed(businessHour.isClosed())
                .openedAt(businessHour.getOpenedAt())
                .closedAt(businessHour.getClosedAt())
                .build();
    }
}
