package com.sparta.vroomvroom.domain.menu.repository;

import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    List<Menu> findAllByCompany_CompanyIdAndIsDeletedFalse(UUID companyId);

    List<Menu> findAllByCompany_CompanyIdAndIsDeletedFalseAndIsVisibleTrue(UUID companyId);
}
