package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpecialBusinessHourRepository extends JpaRepository<SpecialBusinessHour, UUID> {
    List<SpecialBusinessHour> findAllByCompanyId(UUID companyId);
}
