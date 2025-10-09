package com.sparta.vroomvroom.domain.company.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponseDto {

    private String companyName;         // 업체 이름
    private String companyLogo;         // 업체 로고
    private int deliveryFee;            // 배달비
    private String address;             // 주소

    // Entity -> Dto 변환
    public static CompanyResponseDto of(Company company) {
        return CompanyResponseDto.builder()
                .companyName(company.getCompanyName())
                .companyLogo(company.getCompanyLogoUrl())
                .deliveryFee(company.getDeliveryFee())
                .address(company.getAddress())
                .build();
    }
}
