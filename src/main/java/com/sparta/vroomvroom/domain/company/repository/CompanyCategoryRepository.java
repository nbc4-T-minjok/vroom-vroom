package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, UUID> {

    Optional<CompanyCategory> findByCompanyCategoryName(String companyCategoryName);
}
