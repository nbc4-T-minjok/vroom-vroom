package com.sparta.vroomvroom.domain.address.model.entity;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "addresses_id", columnDefinition = "uuid")
    private UUID addressesId;

    @Column(name = "address_name", unique = true, nullable = false, length = 20)
    private String addressName;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(name = "detail_address", nullable = false, length = 50)
    private String detailAddress;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    // 공간데이터 (PostGIS 매핑)
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // requestDto -> entity
    public Address(String addressName, String address, String detailAddress, String zipCode,
                   boolean isDefault, Point location, User user) {
        this.addressName = addressName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
        this.location = location;
        this.user = user;
    }
}
