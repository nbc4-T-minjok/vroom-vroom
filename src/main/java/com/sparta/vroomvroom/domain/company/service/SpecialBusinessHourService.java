package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.request.SpecialBusinessHourRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.SpecialBusinessHourResponseDto;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.company.repository.SpecialBusinessHourRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
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

    public void createSpecialBusinessHour(Long userId, UUID companyId, SpecialBusinessHourRequestDto requestDto) {
        // 유저 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 업체 확인
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 특별영업시간 생성 후 저장
        SpecialBusinessHour specialBusinessHour = new SpecialBusinessHour(company, requestDto);
        specialBusinessHourRepository.save(specialBusinessHour);
    }

    public List<SpecialBusinessHourResponseDto> getSpecialBusinessHours(UUID companyId) {
        List<SpecialBusinessHour> specialBusinessHours = specialBusinessHourRepository.findAllByCompanyId(companyId);
        return specialBusinessHours.stream().map(SpecialBusinessHourResponseDto::of).toList();
    }

    @Transactional
    public SpecialBusinessHourResponseDto updateSpecialBusinessHour(Long userId, UUID companyId, UUID specialBusinessHourId, SpecialBusinessHourRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        SpecialBusinessHour specialBusinessHour = specialBusinessHourRepository.findById(specialBusinessHourId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 특별영엽시간입니다."));

        // Todo : 이 특별 영업시간을 가진 업체의 소유자인지 확인
        // Todo : 요청된 companyId와 특별 영업시간의 companyId가 일치하는지 확인

        specialBusinessHour.update(requestDto);

        return SpecialBusinessHourResponseDto.of(specialBusinessHour);
    }


    public void deleteSpecialBusinessHour(Long userId, UUID companyId, UUID specialBusinessHourId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        SpecialBusinessHour specialBusinessHour = specialBusinessHourRepository.findById(specialBusinessHourId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 특별영엽시간입니다."));

        // Todo : 이 특별 영업시간을 가진 업체의 소유자인지 확인
        // Todo : 요청된 companyId와 특별 영업시간의 companyId가 일치하는지 확인

        specialBusinessHour.softDelete(LocalDateTime.now(), user.getUserName());
    }
}
