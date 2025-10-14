package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BusinessHourRepository extends JpaRepository<BusinessHour, UUID> {
    @Query("SELECT b FROM BusinessHour b WHERE b.company.companyId = :companyId")
    List<BusinessHour> findAllByCompanyId(@Param("companyId") UUID companyId);

    boolean existsByCompanyAndDayAndIsDeletedFalse(Company company, WeekDay day);

    @Query("SELECT b FROM BusinessHour b JOIN FETCH b.company WHERE b.businessHourId = :id")
    Optional<BusinessHour> findWithCompanyById(@Param("id") UUID id);
}
