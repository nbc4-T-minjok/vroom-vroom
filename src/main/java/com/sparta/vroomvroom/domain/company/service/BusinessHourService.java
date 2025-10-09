package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.BusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.BusinessHourRepository;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;
    private final CompanyRepository companyRepository;

    public void createBusinessHour(UUID companyId, BusinessHourRequestDto requestDto) {

        // 업체아이디 검증
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 업체아이디가 존재하지 않습니다."));

        // 영업시간 등록
        BusinessHour businessHour = new BusinessHour(requestDto, company);

    }

}
