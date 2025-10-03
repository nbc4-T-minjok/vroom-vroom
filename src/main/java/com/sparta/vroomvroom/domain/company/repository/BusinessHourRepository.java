package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, UUID> {
}
