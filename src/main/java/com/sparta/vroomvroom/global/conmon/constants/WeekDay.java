package com.sparta.vroomvroom.global.conmon.constants;

import java.time.LocalDate;

public enum WeekDay {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일");

    private final String weekDay;

    WeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public static WeekDay fromLocalDate(LocalDate date) {
        return WeekDay.valueOf(date.getDayOfWeek().name());
    }
}
