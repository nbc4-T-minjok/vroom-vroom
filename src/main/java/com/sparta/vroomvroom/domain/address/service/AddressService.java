package com.sparta.vroomvroom.domain.address.service;

import com.sparta.vroomvroom.domain.address.model.dto.request.AddressRequestDto;
import com.sparta.vroomvroom.domain.address.model.dto.response.AddressResponseDto;
import com.sparta.vroomvroom.domain.address.model.entity.Address;
import com.sparta.vroomvroom.domain.address.repository.AddressRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // 배송지 등록
    public void createAddress(Long userId, AddressRequestDto requestDto) {

        // 사용자 검증
        User user = validateUser(userId);

        // 기본배송지 중복 방지
        if (requestDto.isDefault()) {
            addressRepository.updateIsDefaultToFalse(userId);
        }

        // 우편번호 검증
        String zipCode = requestDto.getZipCode();
        if (!zipCode.matches("\\d{5}")) {
            throw new IllegalArgumentException("우편번호가 올바르지 않습니다. 우편번호는 5자리 숫자여야 합니다.");
        }

        // location 검증
        if (requestDto.getLocation() == null) {
            throw new IllegalArgumentException("location 필드는 필수입니다. (lat, lng 포함)");
        }

        // location에서 lat, lng 꺼내기
        Double lat = requestDto.getLocation().getLat(); // 위도
        Double lng = requestDto.getLocation().getLng(); // 경도

        // 위치 정보 검증
        validateLocation(lat, lng);

        // 포인트 생성
        Point location = createPoint(lat, lng);

        //엔티티 저장
        Address address = new Address(
                requestDto.getAddressName(),
                requestDto.getAddress(),
                requestDto.getDetailAddress(),
                zipCode,
                requestDto.isDefault(),
                location,
                user
        );
        addressRepository.save(address);
    }

    // 배송지 목록 조회 (삭제된 배송지 제외)
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAlladdresses(Long userId) {

        User user = validateUser(userId);
        List<Address> addresses = addressRepository.findAllByUserId(userId);

        List<AddressResponseDto> responseDtos = new ArrayList<>();
        for (Address a : addresses) {
            if(!a.isDeleted()) {
                responseDtos.add(new AddressResponseDto(a));
            }
        }

        return responseDtos;

    }

    // 배송지 수정
    public void patchAddress(Long userId, UUID userAddressId, AddressRequestDto requestDto) {

        User user = validateUser(userId);
        // 배송지 소유 검증
        Address address = validateAddressOwnership(userId, userAddressId);

        // 변경 내용 반영
        if(requestDto.getAddressName() != null) address.setAddressName(requestDto.getAddressName());
        if(requestDto.getAddress() != null) address.setAddress(requestDto.getAddress());
        if(requestDto.getDetailAddress() != null) address.setDetailAddress(requestDto.getDetailAddress());
        if(requestDto.getZipCode() != null) address.setZipCode(requestDto.getZipCode());
        if(requestDto.getLocation() != null) {
            Double lat = requestDto.getLocation().getLat(); // 위도
            Double lng = requestDto.getLocation().getLng(); // 경도
            validateLocation(lat, lng);

            Point location = createPoint(lat, lng);

            address.setLocation(location);
        }
        if (requestDto.isDefault()) {
            addressRepository.updateIsDefaultToFalse(userId);
            address.setDefault(true);
        }
    }


    // 기본배송지 변경
    public void changeDefaultAddress(Long userId, UUID userAddressId) {

        User user = validateUser(userId);
        Address address = validateAddressOwnership(userId, userAddressId);

        // 기존 기본배송지 제거
        addressRepository.updateIsDefaultToFalse(userId);

        // 기본배송지 새로 등록
        address.setDefault(true);
    }

    // 배송지 삭제
    public void deleteAddress(Long userId, UUID userAddressId) {

        User user = validateUser(userId);
        Address address = validateAddressOwnership(userId, userAddressId);

        // 기본배송지가 not null이므로 삭제 불가 처리
        if (address.isDefault()) {
            throw new IllegalArgumentException("기본배송지는 삭제할 수 없습니다.");
        }

        // soft-delete
        address.softDelete(LocalDateTime.now(), user.getUserName());
    }

    // 사용자 검증 메서드
    private User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    // 배송지 소유 검증 메서드
    private Address validateAddressOwnership(Long userId, UUID addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다."));

        if (!address.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인 소유의 배송지만 접근할 수 있습니다.");
        }
        return address;
    }

    // 위치 정보 검증 메서드
    private void validateLocation(Double lat, Double lng) {
        if (lat == null || lng == null) {
            throw new IllegalArgumentException("위도(lat)와 경도(lng)는 모두 필수입니다.");
        }
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도(lat)는 -90 ~ 90 사이의 값이어야 합니다.");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("경도(lng)는 -180 ~ 180 사이의 값이어야 합니다.");
        }
    }

    // 포인트 생성 메서드
    private Point createPoint(Double lat, Double lng) {
        GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coord = new Coordinate(lng, lat); // 경도, 위도 순서로 변경 필수
        Point location = geomFactory.createPoint(coord);
        return location;
    }
}
