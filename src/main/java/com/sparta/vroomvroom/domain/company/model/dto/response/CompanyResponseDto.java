package com.sparta.vroomvroom.domain.company.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

    private UUID companyId;
    private String companyName;         // 업체 이름
    private String companyLogo;         // 업체 로고
    private int deliveryFee;            // 배달비
    private String address;             // 주소

    // Entity -> Dto 변환
    public static CompanyResponseDto of(Company company) {
        return CompanyResponseDto.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companyLogo(company.getCompanyLogoUrl())
                .deliveryFee(company.getDeliveryFee())
                .address(company.getAddress())
                .build();
    }

    public CompanyResponseDto(Company company) {
        this.companyId = company.getCompanyId();
        this.companyName = company.getCompanyName();
        this.companyLogo = company.getCompanyLogoUrl();
        this.deliveryFee = company.getDeliveryFee();
        this.address = company.getAddress();
    }
}
