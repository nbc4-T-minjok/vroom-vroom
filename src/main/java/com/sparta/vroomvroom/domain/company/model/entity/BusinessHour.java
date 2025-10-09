package com.sparta.vroomvroom.domain.company.model.entity;

import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourRequestDto;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "business_hours")
@Getter
@Setter
@NoArgsConstructor
public class BusinessHour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "business_hour_id")
    private UUID businessHourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "day", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private WeekDay day;

    @Column(name = "opened_at", nullable = false)
    private LocalTime openedAt;

    @Column(name = "closed_at", nullable = false)
    private LocalTime closedAt;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    public BusinessHour(BusinessHourRequestDto requestDto, Company company) {

        this.day = requestDto.getDay();
        this.openedAt = requestDto.getOpenedAt();
        this.closedAt = requestDto.getClosedAt();
        this.isClosed = false;
    }
}
