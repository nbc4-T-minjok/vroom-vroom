package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecialBusinessHourRepository extends JpaRepository<SpecialBusinessHour, UUID> {

    boolean existsByCompany_CompanyIdAndDate(UUID companyId, LocalDate date);
    List<SpecialBusinessHour> findAllByCompany_CompanyIdAndIsDeletedFalse(UUID companyId);

}
