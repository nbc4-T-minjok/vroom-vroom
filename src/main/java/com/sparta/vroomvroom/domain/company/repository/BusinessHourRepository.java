package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, UUID> {
    @Query("SELECT b FROM BusinessHour b WHERE b.company.companyId = :companyId")
    BusinessHour findByCompanyId(@Param("companyId") UUID companyId);
}
