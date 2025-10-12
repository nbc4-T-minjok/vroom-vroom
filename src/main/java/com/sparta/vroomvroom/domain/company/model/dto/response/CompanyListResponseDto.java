package com.sparta.vroomvroom.domain.company.model.dto.response;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class CompanyListResponseDto {
    private List<CompanyResponseDto> openCompanies;
    private List<CompanyResponseDto> closedCompanies;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    public static CompanyListResponseDto of(List<CompanyResponseDto> openCompanies
    , List<CompanyResponseDto> closedCompanies, Page<Company> companyPage) {
        return CompanyListResponseDto.builder()
                .openCompanies(openCompanies)
                .closedCompanies(closedCompanies)
                .currentPage(companyPage.getNumber()+1)
                .pageSize(companyPage.getSize())
                .totalPages(companyPage.getTotalPages())
                .totalElements(companyPage.getTotalElements())
                .build();
    }

}
