package com.sparta.vroomvroom.domain.company.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
public class BusinessHourRequestDto {
    private WeekDay day;

    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime openedAt;

    @JsonFormat(pattern = "HH:mm[:ss]")
    private LocalTime closedAt;
}
