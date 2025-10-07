package com.sparta.vroomvroom.domain.address.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class AddressReqeustDto {
    private String addressName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private boolean isDefault;

    private LocationDto location;       // 중첩 객체로 받기

    @Getter
    @NoArgsConstructor
    public static class LocationDto {
        private double lat;
        private double lng;
    }

}
