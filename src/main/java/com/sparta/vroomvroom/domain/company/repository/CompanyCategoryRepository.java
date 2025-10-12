package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, UUID> {

    Optional<CompanyCategory> findByCompanyCategoryNameAndIsDeletedFalse(String companyCategoryName);

    // isDeleted 필드가 false인 항목만 페이징 조회
    Page<CompanyCategory> findAllByIsDeletedFalse(Pageable pageable);
}
