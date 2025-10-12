package com.sparta.vroomvroom.domain.address.controller;

import com.sparta.vroomvroom.domain.address.model.dto.request.AddressRequestDto;
import com.sparta.vroomvroom.domain.address.model.dto.response.AddressResponseDto;
import com.sparta.vroomvroom.domain.address.service.AddressService;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    // 배송지 등록
    @Secured({"ROLE_MANAGER","ROLE_CUSTOMER"})
    @PostMapping("addresses")
    public BaseResponse createAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody AddressRequestDto reqeustDto) {
        Long userId = userDetails.getUser().getUserId();
        addressService.createAddress(userId, reqeustDto);
        return new BaseResponse();
    }

    // 배송지 목록 조회
    @Secured({"ROLE_MANAGER","ROLE_CUSTOMER"})
    @GetMapping("addresses")
    public BaseResponse getAlladdresses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getUserId();
        List<AddressResponseDto> result = addressService.getAlladdresses(userId);
        return new BaseResponse(result);
    }

    // 배송지 정보 수정
    @Secured({"ROLE_MANAGER","ROLE_CUSTOMER"})
    @PatchMapping("addresses/{userAddressId}")
    public BaseResponse patchAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userAddressId, @RequestBody AddressRequestDto requestDto) {
        Long userId = userDetails.getUser().getUserId();
        addressService.patchAddress(userId, userAddressId, requestDto);
        return new BaseResponse<>();
    }


    // 기본배송지 변경
    @Secured({"ROLE_MANAGER","ROLE_CUSTOMER"})
    @PatchMapping("addresses/default/{userAddressId}")
    public BaseResponse changeDefaultAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userAddressId) {
        Long userId = userDetails.getUser().getUserId();
        addressService.changeDefaultAddress(userId, userAddressId);
        return new BaseResponse();
    }

    // 배송지 삭제
    @Secured({"ROLE_MANAGER","ROLE_CUSTOMER"})
    @DeleteMapping("addresses/{userAddressId}")
    public BaseResponse deleteAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable UUID userAddressId) {
        Long userId = userDetails.getUser().getUserId();
        addressService.deleteAddress(userId, userAddressId);
        return new BaseResponse();
    }
}
