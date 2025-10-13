package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.request.SpecialBusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.SpecialBusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.company.repository.SpecialBusinessHourRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.constants.BusinessStatus;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialBusinessHourService {
    private final SpecialBusinessHourRepository specialBusinessHourRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public void createSpecialBusinessHour(User user, UUID companyId, SpecialBusinessHourRequestDto requestDto) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 업체 소유자인지 확인
        validateOwnerOrAdmin(user, company);

        // 중복 날짜 차단
        if (specialBusinessHourRepository.existsByCompany_CompanyIdAndDate(companyId, requestDto.getDate())) {
            throw new IllegalArgumentException("해당 날짜 특별 영업 시간은 이미 등록되어 있습니다.");
        }

        // 영업 상태 검증 로직
        if (requestDto.getBusinessStatus() == BusinessStatus.OPEN) {
            if (requestDto.getOpenedAt() == null || requestDto.getClosedAt() == null) {
                throw new IllegalArgumentException("영업중(OPEN) 상태로 변경하려면 오픈 및 마감 시간을 모두 입력해야 합니다.");
            }

            // 같은 오픈/마감시간 금지
            if (requestDto.getOpenedAt().equals(requestDto.getClosedAt())) {
                throw new IllegalArgumentException("오픈 시간과 마감 시간이 동일할 수 없습니다.");
            }
        }

        // 특별영업시간 생성 후 저장
        SpecialBusinessHour specialBusinessHour = new SpecialBusinessHour(company, requestDto);
        specialBusinessHourRepository.save(specialBusinessHour);
    }

    public List<SpecialBusinessHourResponseDto> getSpecialBusinessHours(UUID companyId) {
        List<SpecialBusinessHour> specialBusinessHours = specialBusinessHourRepository.findAllByCompany_CompanyIdAndIsDeletedFalse(companyId);
        return specialBusinessHours.stream().map(SpecialBusinessHourResponseDto::of).toList();
    }

    @Transactional
    public SpecialBusinessHourResponseDto updateSpecialBusinessHour(User user, UUID companyId, UUID specialBusinessHourId, SpecialBusinessHourRequestDto requestDto) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        SpecialBusinessHour specialBusinessHour = specialBusinessHourRepository.findById(specialBusinessHourId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 특별영엽시간입니다."));

        // 업체 소유자인지 확인
        validateOwnerOrAdmin(user, company);

        // 요청된 companyId와 특별 영업시간의 companyId가 일치하는지 확인
        if (!specialBusinessHour.getCompany().getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("해당 특별 영업시간은 업체 ID: " + companyId + "에 속하지 않습니다.");
        }

        specialBusinessHour.update(requestDto);

        // 영업 상태 검증 로직
        if (requestDto.getBusinessStatus() == BusinessStatus.OPEN) {
            if (requestDto.getOpenedAt() == null || requestDto.getClosedAt() == null) {
                throw new IllegalArgumentException("영업중(OPEN) 상태로 변경하려면 오픈 및 마감 시간을 모두 입력해야 합니다.");
            }

            // 같은 오픈/마감시간 금지
            if (requestDto.getOpenedAt().equals(requestDto.getClosedAt())) {
                throw new IllegalArgumentException("오픈 시간과 마감 시간이 동일할 수 없습니다.");
            }
        }

        return SpecialBusinessHourResponseDto.of(specialBusinessHour);
    }

    @Transactional
    public void deleteSpecialBusinessHour(User user, UUID companyId, UUID specialBusinessHourId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        SpecialBusinessHour specialBusinessHour = specialBusinessHourRepository.findById(specialBusinessHourId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 특별영엽시간입니다."));

        // 업체 소유자인지 확인
        validateOwnerOrAdmin(user, company);

        // 요청된 companyId와 특별 영업시간의 companyId가 일치하는지 확인
        if (!specialBusinessHour.getCompany().getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("해당 특별 영업시간은 업체 ID: " + companyId + "에 속하지 않습니다.");
        }

        specialBusinessHour.softDelete(LocalDateTime.now(), user.getUserName());
    }

    // 업체의 소유자인지 확인하는 유틸 메서드
    private void validateOwnerOrAdmin(User user, Company company) {
        boolean isOwner = company.getUser().getUserId().equals(user.getUserId());
        boolean isAdmin = user.getRole() == UserRole.ROLE_MANAGER;

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("해당 업체의 소유자만 접근할 수 있습니다.");
        }
    }
}
