package com.sparta.vroomvroom.domain.address.repository;


import com.sparta.vroomvroom.domain.address.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
