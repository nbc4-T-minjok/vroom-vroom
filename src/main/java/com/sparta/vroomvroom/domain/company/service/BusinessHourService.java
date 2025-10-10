package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.BusinessHourRepository;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;
    private final CompanyRepository companyRepository;

    // 영업시간 등록
    public void createBusinessHour(UUID companyId, BusinessHourRequestDto requestDto) {

        // 업체아이디 검증
        Company company = validateCompany(companyId);

        // 동일 요일 중복 등록 방지
        boolean exists = businessHourRepository.existsByCompanyAndDay(company, requestDto.getDay());
        if (exists) {
            throw new IllegalArgumentException("이미 해당 요일의 영업시간이 등록되어 있습니다.");
        }

        // 영업시간 등록
        BusinessHour businessHour = new BusinessHour(requestDto, company);
        businessHourRepository.save(businessHour);
    }

    // 영업시간 조회
    public List<BusinessHourResponseDto> getBusinessHour(UUID companyId) {
        // 업체아이디 검증
        Company company = validateCompany(companyId);

        // 영업시간 조회
        List<BusinessHour> businessHours = businessHourRepository.findAllByCompanyId(companyId);

        List<BusinessHourResponseDto> responseDtos = new ArrayList<>();
        for(BusinessHour b : businessHours) {
            responseDtos.add(new BusinessHourResponseDto(b));
        }

        return responseDtos;
    }

    public void patchBusinessHour(UUID companyId, UUID businessHourId, BusinessHourRequestDto requestDto) {
        // 업체아이디 검증
        Company company = validateCompany(companyId);

        // 영업시간아이디 검증
        BusinessHour businessHour = validateBusinessHourOwnership(businessHourId, companyId);

        // 영업시간 수정
        if(requestDto.getDay() != null) businessHour.setDay(requestDto.getDay());
        if(requestDto.getOpenedAt() != null) businessHour.setOpenedAt(requestDto.getOpenedAt());
        if(requestDto.getClosedAt() != null) businessHour.setClosedAt((requestDto.getClosedAt()));
        businessHour.setClosed(requestDto.isClosed());    // 영업일 <--> 정기휴뮤일

        businessHourRepository.save(businessHour);
    }

    public void deleteBusinessHour(UUID companyId, UUID businessHourId, String userName) {
        // 업체아이디 검증
        Company company = validateCompany(companyId);

        // 영업시간아이디 검증
        BusinessHour businessHour = validateBusinessHourOwnership(businessHourId, companyId);

        // 영업시간 삭제
        businessHour.softDelete(LocalDateTime.now(), userName);
        businessHourRepository.save(businessHour);
    }

    // 업체 검증 메서드
    private Company validateCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 업체아이디가 존재하지 않습니다."));
        return company;
    }

    // 영업시간 소유 검증 메서드
    private BusinessHour validateBusinessHourOwnership(UUID businessHourId, UUID companyId) {
        // 영업시간아이디 검증
        BusinessHour businessHour = businessHourRepository.findById(businessHourId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 영업시간아이디가 존지하지 않습니다."));

        // 권한 검증 (companyId 일치 확인)
        if (!businessHour.getCompany().getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("해당 업체의 영업시간이 아닙니다.");
        }

        return businessHour;
    }
}

/*
*** 임시데이터 ***
TODO. 업체 머지 후 삭제
INSERT INTO company_categories (
  company_category_id,
  company_category_name,
  created_at,
  created_by,
  updated_at,
  updated_by,
  is_deleted
) VALUES (
  '11111111-1111-1111-1111-111111111111',
  '카페',
  NOW(),
  'user1',
  NOW(),
  'user1',
  FALSE
);

INSERT INTO companies (
  company_id,
  company_category_id,
  company_name,
  company_logo_url,
  company_description,
  phone_number,
  delivery_fee,
  delivery_radius,
  owner_name,
  biz_reg_no,
  address,
  detail_address,
  zip_code,
  created_at,
  created_by,
  updated_at,
  updated_by,
  is_deleted
) VALUES (
  '22222222-2222-2222-2222-222222222222', -- companyId
  '11111111-1111-1111-1111-111111111111', -- companyCategoryId
  '00커피',
  'https://example.com/logo.png',
  '00커피',
  '02-123-4567',
  2000,
  3000,
  'owner',
  '123-45-67890',
  '서울특별시 ~',
  '1층',
  '04536',
  NOW(),
  'user1',
  NOW(),
  'user1',
  FALSE
);

 */
