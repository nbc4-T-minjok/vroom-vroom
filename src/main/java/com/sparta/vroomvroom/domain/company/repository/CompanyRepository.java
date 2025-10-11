package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsByBizRegNo(String bizRegNo);

    List<Company> findAllByIsDeletedFalse();

    Page<Company> findAllByIsDeletedFalseAndCompanyCategory_CompanyCategoryId(UUID companyCategoryId, Pageable pageable);
}
