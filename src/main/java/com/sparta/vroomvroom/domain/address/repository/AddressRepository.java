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

    /*
    벌크 업데이트 후에 해당 엔티티를 재조회 하지 않는 경우 컨텍스트 비워서 DB/메모리 불일치 방지하는게 맞음 -> true
    하지만 아래처럼 벌크 직후 같은 엔티티 수정 이어지는 경우 컨텍스트가 비워지면 관리가 끊겨서 더티체킹 불가능 -> false
     */
    @Modifying(clearAutomatically = false)
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.user.userId = :userId")
    void updateIsDefaultToFalse(@Param("userId") Long userId);

    @Query("SELECT a FROM Address a WHERE a.user.userId = :userId")
    List<Address> findAllByUserId(@Param("userId") Long userId);

}
