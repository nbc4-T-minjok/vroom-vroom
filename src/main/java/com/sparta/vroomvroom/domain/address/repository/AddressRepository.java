package com.sparta.vroomvroom.domain.address.repository;


import com.sparta.vroomvroom.domain.address.model.dto.response.AddressResponseDto;
import com.sparta.vroomvroom.domain.address.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.userId = :userId")
    void updateIsDefaultToFalse(@Param("userId") Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.userId = :userId")
    List<Address> findAllByUserId(Long userId);
}
