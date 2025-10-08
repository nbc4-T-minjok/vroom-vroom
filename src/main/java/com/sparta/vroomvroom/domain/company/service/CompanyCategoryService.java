package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryRequestDto;
import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import com.sparta.vroomvroom.domain.company.repository.CompanyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCategoryService {
    private final CompanyCategoryRepository companyCategoryRepository;

    public void createCompanyCategories(CompanyCategoryRequestDto requestDto) {
        // Todo : 회원의 권한 체크

        // 중복 체크
        companyCategoryRepository.findByCompanyCategoryName(requestDto.getCompanyCategoryName())
                .ifPresent(category -> {
                    // 이미 존재한다면 예외를 던집니다.
                    throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다: ");
                });

        // 카테고리 저장
        CompanyCategory companyCategory = new CompanyCategory(requestDto);

        // Todo : 감사로그
        companyCategory.create("test userName");

        companyCategoryRepository.save(companyCategory);
    }
}
