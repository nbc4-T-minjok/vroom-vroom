package com.sparta.vroomvroom.domain.company.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CompanyRequestDto {

    @NotBlank(message = "업체명은 필수입니다.")
    @Size(max = 20, message = "업체명은 20자 이내여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "업체명은 한글, 영문, 숫자만 사용할 수 있습니다.")
    private String companyName;

    private String companyLogoUrl;

    @NotBlank(message = "업체 설명은 필수입니다.")
    @Size(max = 500, message = "업체 설명은 최대 500자까지 가능합니다.")
    private String companyDescription;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호는 000-0000-0000 형식이어야 합니다.")
    private String phoneNumber;

    @Min(value = 0, message = "배달비는 0 이상이어야 합니다.")
    private int deliveryFee;

    @Min(value = 0, message = "배달 반경은 0 이상이어야 합니다.")
    private int deliveryRadius; // 단위: m

    @NotBlank(message = "대표자명은 필수입니다.")
    @Size(max = 20, message = "대표자명은 20자 이내여야 합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "대표자명은 한글 또는 영문만 입력할 수 있습니다.")
    private String ownerName;

    @NotBlank(message = "사업자등록번호는 필수입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자등록번호는 000-00-00000 형식이어야 합니다.")
    private String bizRegNo;

    @NotBlank(message = "주소는 필수입니다.")
    @Size(max = 50, message = "주소는 최대 50자까지 가능합니다.")
    private String address;

    @NotBlank(message = "상세주소는 필수입니다.")
    @Size(max = 50, message = "상세주소는 최대 50자까지 가능합니다.")
    private String detailAddress;

    @NotBlank(message = "우편번호는 필수입니다.")
    @Pattern(regexp = "^\\d{5}$", message = "우편번호는 5자리 숫자여야 합니다.")
    private String zipCode;

    @Valid
    @NotNull(message = "위치 정보(location)는 필수입니다.")
    private LocationDto location;

    @Getter
    @NoArgsConstructor
    public static class LocationDto {
        @NotNull(message = "위도(lat)는 필수입니다.")
        @DecimalMin(value = "-90.0", message = "위도(lat)는 -90 이상이어야 합니다.")
        @DecimalMax(value = "90.0", message = "위도(lat)는 90 이하이어야 합니다.")
        private Double lat;

        @NotNull(message = "경도(lng)는 필수입니다.")
        @DecimalMin(value = "-180.0", message = "경도(lng)는 -180 이상이어야 합니다.")
        @DecimalMax(value = "180.0", message = "경도(lng)는 180 이하이어야 합니다.")
        private Double lng;
    }
}