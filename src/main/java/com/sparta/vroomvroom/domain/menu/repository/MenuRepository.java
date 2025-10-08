package com.sparta.vroomvroom.domain.menu.repository;

import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

    List<Menu> findAllByCompanyIdAndIsDeletedFalse(UUID companyId);

    List<Menu> findAllByCompanyIdAndIsDeletedFalseAndIsVisibleTrue(UUID companyId);

}
