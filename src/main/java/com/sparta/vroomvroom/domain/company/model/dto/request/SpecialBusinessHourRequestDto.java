package com.sparta.vroomvroom.domain.company.model.dto.request;

import com.sparta.vroomvroom.global.conmon.constants.BusinessStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class SpecialBusinessHourRequestDto {

    @NotNull(message = "특별 영업일은 필수 입력 항목입니다.")
    @FutureOrPresent(message = "특별 영업일은 현재 날짜 또는 미래 날짜만 가능합니다.")
    private LocalDate date;

    private LocalTime openedAt;
    private LocalTime closedAt;

    @NotNull(message = "영업 상태는 필수 입력 항목입니다.")
    private BusinessStatus businessStatus;
}
