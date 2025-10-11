package com.sparta.vroomvroom.domain.company.model.dto;

import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyCategoryResponseDto {
    private String companyCategoryName;

    // Entity를 DTO로 변환하는 정적 팩토리 메서드
    public static CompanyCategoryResponseDto of(CompanyCategory category) {
        return CompanyCategoryResponseDto.builder()
                .companyCategoryName(category.getCompanyCategoryName())
                .build();
    }
}
