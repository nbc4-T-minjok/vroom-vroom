package com.sparta.vroomvroom.domain.company.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class BusinessHourResponseDto {
    private UUID businessHourId;
    private WeekDay day;

    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime openedAt;

    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime closedAt;

    private boolean isDeleted;

    public BusinessHourResponseDto(BusinessHour businessHour) {
        this.businessHourId = businessHour.getBusinessHourId();
        this.day = businessHour.getDay();
        this.openedAt = businessHour.getOpenedAt();
        this.closedAt = businessHour.getClosedAt();
        this.isDeleted = businessHour.isDeleted();
    }
}
