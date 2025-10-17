package com.sparta.vroomvroom.domain.company.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.dto.request.CompanyRequestDto.LocationDto;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CompanyDetailResponseDto {
    // 1. 기본 정보
    private String companyCategoryName; // 카테고리 이름
    private String companyName;         // 업체 이름
    private String companyLogo;         // 업체 로고
    private String companyDescription;  // 업체 설명
    private String phoneNumber;         // 전화번호

    // 2. 배달 정보
    private Integer deliveryFee;        // 배달비
    private Integer deliveryRadius;     // 배달 반경

    // 3. 사업자 정보
    private String ownerName;           // 대표자명
    private String bizRegNo;            // 사업자 등록번호

    // 4. 주소 및 위치 정보
    private String address;             // 주소
    private String detailAddress;       // 상세 주소
    private String zipCode;             // 우편번호

//    // 5. 영업 시간 정보
    private List<BusinessHourResponseDto> businessHours;         // 영업시간
    private List<SpecialBusinessHourResponseDto> specialBusinessHours; // 특별 영업시간

    // Entity -> Dto 변환 메서드
    public static CompanyDetailResponseDto of(Company company) {
        return CompanyDetailResponseDto.builder()
                .companyCategoryName(company.getCompanyCategory().getCompanyCategoryName())
                .companyName(company.getCompanyName())
                .companyLogo(company.getCompanyLogoUrl())
                .companyDescription(company.getCompanyDescription())
                .phoneNumber(company.getPhoneNumber())
                .deliveryFee(company.getDeliveryFee())
                .deliveryRadius(company.getDeliveryRadius())
                .ownerName(company.getOwnerName())
                .bizRegNo(company.getBizRegNo())
                .address(company.getAddress())
                .detailAddress(company.getDetailAddress())
                .zipCode(company.getZipCode())
                .businessHours(
                        company.getBusinessHours().stream()
                                .map(BusinessHourResponseDto::of)
                                .toList())
                .specialBusinessHours(
                        company.getSpecialBusinessHours().stream()
                                .map(SpecialBusinessHourResponseDto::of)
                                .toList())
                .build();
    }

    @Getter
    public static class LocationDto {
        private final double lat;
        private final double lng;
        public LocationDto(org.locationtech.jts.geom.Point p) {
            this.lat = p.getY(); // 위도
            this.lng = p.getX(); // 경도
        }
    }

}
