package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.CompanyCategoryResponseDto;
import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import com.sparta.vroomvroom.domain.company.repository.CompanyCategoryRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyCategoryService {
    private final CompanyCategoryRepository companyCategoryRepository;

    public void createCompanyCategories(CompanyCategoryRequestDto requestDto) {

        // 중복 체크
        companyCategoryRepository.findByCompanyCategoryNameAndIsDeletedFalse(requestDto.getCompanyCategoryName())
                .ifPresent(category -> {
                    throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다.");
                });

        // 카테고리 저장
        CompanyCategory companyCategory = new CompanyCategory(requestDto);

        companyCategoryRepository.save(companyCategory);
    }

    public Page<CompanyCategoryResponseDto> getCompanyCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyCategory> categoryPage = companyCategoryRepository.findAllByIsDeletedFalse(pageable);
        return categoryPage.map(CompanyCategoryResponseDto::of);
    }

    @Transactional
    public CompanyCategoryResponseDto updateCompanyCategories(UUID companyCategoryId, CompanyCategoryRequestDto requestDto) {
        // ID 조회 - 없으면 예외
        CompanyCategory companyCategory = companyCategoryRepository.findById(companyCategoryId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않은 카테고리입니다.");
                });

        // 데이터 변경
        companyCategory.update(requestDto);

        // 엔티티에서 responseDto 로 변환 후 반환
        return CompanyCategoryResponseDto.of(companyCategory);
    }

    @Transactional
    public void deleteCompanyCategories(User user, UUID companyCategoryId) {

        // ID 조회 - 없으면 예외
        CompanyCategory companyCategory = companyCategoryRepository.findById(companyCategoryId)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("존재하지 않은 카테고리입니다.");
                });

        companyCategory.softDelete(LocalDateTime.now(), user.getUserName());
    }

    public Page<CompanyCategoryResponseDto> getAdminCompanyCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyCategory> categoryPage = companyCategoryRepository.findAll(pageable);
        return categoryPage.map(CompanyCategoryResponseDto::of);
    }
}
