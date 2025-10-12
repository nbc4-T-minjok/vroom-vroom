package com.sparta.vroomvroom.domain.address.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.vroomvroom.domain.address.model.entity.Address;
import lombok.Getter;
import org.locationtech.jts.geom.Point;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressResponseDto {
    private UUID addressId;
    private String addressName;
    private String address;
    private String detailAddress;
    private String zipCode;

    @JsonProperty("isDefault")   // boolean 이름 유지
    private boolean isDefault;

    private LocationDto location;  // Point 대신 DTO로 변환

    @Getter
    public static class LocationDto {
        private final double lat;
        private final double lng;
        public LocationDto(org.locationtech.jts.geom.Point p) {
            this.lat = p.getY(); // 위도
            this.lng = p.getX(); // 경도
        }
    }

    // entity -> responseDto
    public AddressResponseDto(Address a) {
        this.addressId = a.getAddressesId();
        this.addressName = a.getAddressName();
        this.address = a.getAddress();
        this.detailAddress = a.getDetailAddress();
        this.zipCode = a.getZipCode();
        this.isDefault = a.isDefault();
        if (a.getLocation() != null) {
            this.location = new LocationDto(a.getLocation());
        }
    }
}

