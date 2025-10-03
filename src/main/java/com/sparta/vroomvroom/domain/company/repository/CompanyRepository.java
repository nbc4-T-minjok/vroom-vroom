package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
