package com.sparta.vroomvroom.domain.company.model.entity;

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
public class SpecialBusinessHour {
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
}
