package com.sparta.vroomvroom.domain.address.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class AddressRequestDto {
    private String addressName;
    private String address;
    private String detailAddress;
    private String zipCode;
    @JsonProperty("isDefault")
    private boolean isDefault;

    private LocationDto location;       // 중첩 객체로 받기

    @Getter
    @NoArgsConstructor
    public static class LocationDto {
        private double lat;
        private double lng;
    }

}
