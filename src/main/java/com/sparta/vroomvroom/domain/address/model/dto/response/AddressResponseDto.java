package com.sparta.vroomvroom.domain.address.model.dto.response;

import com.sparta.vroomvroom.domain.address.model.entity.Address;
import org.locationtech.jts.geom.Point;
import lombok.Setter;


import java.awt.*;

@Setter
public class AddressResponseDto {
    private String addressName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private boolean isDefault;
    private Point location;

    // 엔티티->dto
    public AddressResponseDto(String addressName,String address, String detailAddress,
                              String zipCode, boolean isDefault, Point location) {
        this.addressName = addressName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
        this.location = location;
    }

    // 엔티티 기반 생성자 (선택)
    public AddressResponseDto(Address address) {
        this.addressName = address.getAddressName();
        this.address = address.getAddress();
        this.detailAddress = address.getDetailAddress();
        this.zipCode = address.getZipCode();
        this.isDefault = address.isDefault();
        this.location = address.getLocation();
    }
}
