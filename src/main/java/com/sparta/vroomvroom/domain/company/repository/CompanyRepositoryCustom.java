package com.sparta.vroomvroom.domain.company.repository;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompanyRepositoryCustom {
    Page<Company> searchByKeywordAndLocation(String keyword, Point location, Pageable pageable);
    Page<Company> searchByCategoryAndLocation(UUID categoryId, Point location, Pageable pageable);
}
