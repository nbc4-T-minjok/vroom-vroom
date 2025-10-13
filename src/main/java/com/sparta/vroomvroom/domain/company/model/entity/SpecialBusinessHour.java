package com.sparta.vroomvroom.domain.company.model.entity;

import com.sparta.vroomvroom.domain.company.model.dto.request.SpecialBusinessHourRequestDto;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import com.sparta.vroomvroom.global.conmon.constants.BusinessStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "special_business_hours")
@Getter
@Setter
@NoArgsConstructor
public class SpecialBusinessHour extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "special_business_hour_id")
    private UUID specialBusinessHourId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "opened_at")
    private LocalTime openedAt;

    @Column(name = "closed_at")
    private LocalTime closedAt;

    @Column(name = "business_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BusinessStatus businessStatus;

    // Dto -> Entity 변환
    public SpecialBusinessHour(Company company, SpecialBusinessHourRequestDto requestDto) {
        this.company = company;
        this.date = requestDto.getDate();
        this.openedAt = requestDto.getOpenedAt();
        this.closedAt = requestDto.getClosedAt();
        this.businessStatus = requestDto.getBusinessStatus();
    }

    public void update(SpecialBusinessHourRequestDto requestDto) {
        this.date = requestDto.getDate();
        this.openedAt = requestDto.getOpenedAt();
        this.closedAt = requestDto.getClosedAt();
        this.businessStatus = requestDto.getBusinessStatus();
    }
}
