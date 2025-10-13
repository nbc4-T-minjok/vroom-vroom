package com.sparta.vroomvroom.domain.company.model.entity;

import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.util.*;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "company_id")
    private UUID companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_category_id", nullable = false)
    private CompanyCategory companyCategory;

    @Column(name = "company_name", nullable = false, length = 20)
    private String companyName;

    @Column(name = "company_logo_url", nullable = false)
    private String companyLogoUrl;

    @Column(name = "company_description", nullable = false)
    private String companyDescription;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "delivery_fee", nullable = false)
    private int deliveryFee;

    @Column(name = "delivery_radius", nullable = false)
    private int deliveryRadius;

    @Column(name = "owner_name", nullable = false, length = 20)
    private String ownerName;

    @Column(name = "biz_reg_no", nullable = false, length = 20)
    private String bizRegNo;

    @Column(name = "address", nullable = false, length = 50)
    private String address;

    @Column(name = "detail_address", nullable = false, length = 50)
    private String detailAddress;

    @Column(name = "zip_code", nullable = false, length = 10)
    private String zipCode;

    // 공간데이터 (PostGIS 매핑)
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

}
