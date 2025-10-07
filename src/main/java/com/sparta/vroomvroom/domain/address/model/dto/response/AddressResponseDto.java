package com.sparta.vroomvroom.domain.address.model.dto.response;

import com.sparta.vroomvroom.domain.address.model.entity.Address;
import org.locationtech.jts.geom.Point;
import lombok.Setter;

@Setter
public class AddressResponseDto {
    private String addressName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private boolean isDefault;
    private Point location;

    // entity -> responseDto
    public AddressResponseDto(Address address) {
        this.addressName = address.getAddressName();
        this.address = address.getAddress();
        this.detailAddress = address.getDetailAddress();
        this.zipCode = address.getZipCode();
        this.isDefault = address.isDefault();
        this.location = address.getLocation();
    }
}
