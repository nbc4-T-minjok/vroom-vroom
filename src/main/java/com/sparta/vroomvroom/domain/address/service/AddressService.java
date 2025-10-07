package com.sparta.vroomvroom.domain.address.service;

import com.sparta.vroomvroom.domain.address.model.dto.request.AddressReqeustDto;
import com.sparta.vroomvroom.domain.address.model.dto.response.AddressResponseDto;
import com.sparta.vroomvroom.domain.address.model.entity.Address;
import com.sparta.vroomvroom.domain.address.repository.AddressRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // 배송지 등록
    public void createAddress(Long userId, AddressReqeustDto requestDto) {

        // 사용자 검증
        User user = userRepository.findById((userId)).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 기본배송지 중복 방지
        if (requestDto.isDefault()) {
            addressRepository.updateIsDefaultToFalse(userId);
        }

        // 우편번호 검증
        String zipCode = requestDto.getZipCode();
        if(!zipCode.matches("\\d{5}")) {
           throw new IllegalArgumentException("우편번호가 올바르지 않습니다. 우편번호는 5자리 숫자여야 합니다.");
        }

        // 위치 정보 검증
        // location에서 lat, lng 꺼내기
        if (requestDto.getLocation() == null) {
            throw new IllegalArgumentException("location 필드는 필수입니다. (lat, lng 포함)");
        }

        Double lat = requestDto.getLocation().getLat(); // 위도
        Double lng = requestDto.getLocation().getLng(); // 경도

        if (lat == null || lng == null) {
            throw new IllegalArgumentException("위도(lat)와 경도(lng)는 모두 필수입니다.");
        }
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도(lat)는 -90 ~ 90 사이의 값이어야 합니다.");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("경도(lng)는 -180 ~ 180 사이의 값이어야 합니다.");
        }

        // 포인트 생성
        GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coord = new Coordinate(lng, lat); // 경도, 위도 순서로 변경 필수
        Point location = geomFactory.createPoint(coord);

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

    // 배송지 목록 조회
    public List<AddressResponseDto> getAlladdresses(Long userId) {
        // 사용자 검증
        User user = userRepository.findById((userId)).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 배송지 목록 조회
        List<Address> addresses = addressRepository.findAllByUserId(userId);

        List<AddressResponseDto> responseDtos = new ArrayList<>();
        for(Address a : addresses) {
            responseDtos.add(new AddressResponseDto(a));
        }

        return responseDtos;

    }

    // 배송지 수정

    // 기본배송지 변경

    // 배송지 삭제
}
