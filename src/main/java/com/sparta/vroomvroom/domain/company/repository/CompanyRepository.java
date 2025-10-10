package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsByBizRegNo(String bizRegNo);

    List<Company> findAllByIsDeletedFalse();

    // find All By [CompanyCategory ID] And [IsDeleted False]
    List<Company> findAllByCompanyCategoryIdAndIsDeletedFalse(UUID companyCategoryId);
}
