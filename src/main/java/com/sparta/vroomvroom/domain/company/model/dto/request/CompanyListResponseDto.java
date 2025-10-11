package com.sparta.vroomvroom.domain.company.model.dto.request;

import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CompanyListResponseDto {
    private List<CompanyResponseDto> openCompanies;
    private List<CompanyResponseDto> closedCompanies;
    private int totalPages;
}
