package com.sparta.vroomvroom.domain.address.controller;

import com.sparta.vroomvroom.domain.address.model.dto.request.AddressReqeustDto;
import com.sparta.vroomvroom.domain.address.model.dto.response.AddressResponseDto;
import com.sparta.vroomvroom.domain.address.service.AddressService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

//    //Todo: PR 병합시 삭제
//    @Secured({"ROLE_MANAGER","ROLE_CUSTOMER"})
//    @GetMapping("/users/login/test")
//    public BaseResponse testLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        System.out.println(userDetails.getUser().getUserName());
//        System.out.println(userDetails.getUser().getUserId());
//        return new BaseResponse();
//    }

    // 배송지 등록
    @PostMapping("addresses")
    //public BaseResponse createAddress(@AuthenticationPrincipal UserDetailsImpl userDetails)
    public BaseResponse createAddress(@RequestBody AddressReqeustDto reqeustDto){
        addressService.createAddress(Long userId, reqeustDto);
        return new BaseResponse();
    }

    // 배송지 목록 조회
    @GetMapping("addresses")
    //public BaseResponse createAddress(@AuthenticationPrincipal UserDetailsImpl userDetails)
    public BaseResponse getAlladdresses() {
        List<AddressResponseDto> result = addressService.getAlladdresses(Long userId);
        return new BaseResponse(result);
    }

    // 배송지 정보 수정
    @PatchMapping("addresses/{userAddressId}")
    public BaseResponse patchAddress(Long userId, @RequestParam UUID userAddressId, @RequestBody AddressReqeustDto reqeustDto) {
        addressService.patchAddress(userId, userAddressId, reqeustDto);
        return new BaseResponse<>();
    }


    // 기본배송지 변경
    @PatchMapping("addresses/default/{userAddressId}")
    //public BaseResponse changeDefaultAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam UUID userAddressId))
    public BaseResponse changeDefaultAddress(Long userId, @RequestParam UUID userAddressId) {
        addressService.changeDefaultAddress(userId, userAddressId);
        return new BaseResponse();
    }

    // 배송지 삭제
    @DeleteMapping("addresses/{userAddressId}")
    //public BaseResponse deleteAddress(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam UUID userAddressId))
    public BaseResponse deleteAddress(Long userId, @RequestParam UUID userAddressId) {
        addressService.deleteAddress(userId, userAddressId);
        return new BaseResponse();
    }
}
