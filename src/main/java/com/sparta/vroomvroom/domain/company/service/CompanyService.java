package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyListResponseDto;
import com.sparta.vroomvroom.domain.company.model.dto.request.CompanyRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyDetailResponseDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyResponseDto;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import com.sparta.vroomvroom.domain.company.repository.CompanyCategoryRepository;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.constants.BusinessStatus;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import com.sparta.vroomvroom.global.conmon.constants.WeekDay;
import com.sparta.vroomvroom.global.conmon.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyCategoryRepository companyCategoryRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    //업체 등록
    @Transactional
    public void createCompany(Long userId, UUID companyCategoryId, CompanyRequestDto requestDto, MultipartFile logoFile) {
        // 유저 존재 및 권한 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (user.getRole().equals(UserRole.ROLE_CUSTOMER)) { // 예시: UserRole.OWNER
            throw new IllegalArgumentException("해당 사용자는 권한이 없습니다.");
        }

        // 업체 카테고리 확인
        CompanyCategory category = companyCategoryRepository.findById(companyCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체 카테고리입니다."));

        // 사업자등록번호 중복 확인
        if (companyRepository.existsByBizRegNo(requestDto.getBizRegNo())) {
            throw new IllegalArgumentException("이미 등록된 사업자등록번호입니다.");
        }

        // s3 사진 업로드 후 url 반환
        String companyLogoUrl = s3Uploader.upload(logoFile, "companyLogoFile");

        // 업체 생성 및 저장
        Company company = new Company(user, category, requestDto, companyLogoUrl);
        companyRepository.save(company);
    }

    public CompanyDetailResponseDto getCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        return CompanyDetailResponseDto.of(company);
    }

    //카테고리별 업체 목록 조회 (영업 중인 업체와 영업 중이 아닌 업체 별도 조회)
    public CompanyListResponseDto getCompaniesByCategory(int page, int size, String sortBy, boolean isAsc, UUID companyCategoryId) {

        //페이징 처리 세팅
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        //영업 여부 판별에 필요한 날짜 값 세팅
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        WeekDay todayDay = WeekDay.fromLocalDate(today);

        //카테고리와 일치하고 삭제되지 않은 업체 조회
        Page<Company> companyPage =
                companyRepository.findAllByIsDeletedFalseAndCompanyCategory_CompanyCategoryId(
                        companyCategoryId, pageable);

        List<CompanyResponseDto> openConpanies = new ArrayList<>();
        List<CompanyResponseDto> closedConpanies = new ArrayList<>();

        //각 업체마다 영업시간 판별
        for(Company company : companyPage.getContent()) {
            boolean isOpen = isCompanyOpen(company, today, currentTime);
            CompanyResponseDto dto = new CompanyResponseDto(company);
            if(isOpen) openConpanies.add(dto);
            else closedConpanies.add(dto);
        }
        return CompanyListResponseDto.of(openConpanies,closedConpanies,companyPage);
    }


    public CompanyListResponseDto searchByKeyword(String keyword, int page, int size, String sortBy, boolean isAsc) {
        //페이징 처리 세팅
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;

        //기본 2차정렬 세팅
        if(sortBy == null || sortBy.isEmpty()) sortBy = "companyName";
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        //영업 여부 판별에 필요한 날짜 값 세팅
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        WeekDay todayDay = WeekDay.fromLocalDate(today);

        //카테고리와 일치하고 삭제되지 않은 업체 조회
        Page<Company> companyPage =
                companyRepository.searchByKeyword(keyword, pageable);

        List<CompanyResponseDto> openConpanies = new ArrayList<>();
        List<CompanyResponseDto> closedConpanies = new ArrayList<>();

        //각 업체마다 영업시간 판별
        for(Company company : companyPage.getContent()) {
            boolean isOpen = isCompanyOpen(company, today, currentTime);
            CompanyResponseDto dto = new CompanyResponseDto(company);
            if(isOpen) openConpanies.add(dto);
            else closedConpanies.add(dto);
        }
        return CompanyListResponseDto.of(openConpanies,closedConpanies,companyPage);
    }

    @Transactional
    public CompanyDetailResponseDto updateCompany(Long userId, UUID companyId, CompanyRequestDto requestDto, MultipartFile logoFile) {
        // 유저 존재 및 권한 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 업체 확인
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // s3 사진 업데이트
        String oldLogoUrl = company.getCompanyLogoUrl();
        String newLogoUrl = s3Uploader.update(logoFile, oldLogoUrl, "companyLogoFile");

        // 업체 수정
        company.update(requestDto, newLogoUrl);

        // 엔티티에서 responseDto 로 변환 후 반환
        return CompanyDetailResponseDto.of(company);
    }

    @Transactional
    public void deleteCompany(Long userId, UUID companyId) {
        // 유저 존재 및 권한 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 업체 확인
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 업체 삭제
        company.softDelete(LocalDateTime.now(), user.getUserName());
    }

    //---유틸 함수

    //영업시간 판별
    //오늘이 특별 영업시간에 포함되면 일반 영업은 그 날짜에서 고려하지 않아도 됨
    //당일,익일 나눠서 고려
    private boolean isCompanyOpen(Company company, LocalDate today, LocalTime now) {
        //오늘 특별영업 여부 확인
        SpecialBusinessHour todaySpecial = findSpecialBusinessHour(company, today);
        if (todaySpecial != null) {
            return isOpenBySpecial(todaySpecial, now);
        }

        //어제 특별영업이 오늘 새벽까지 이어지는지 확인
        SpecialBusinessHour yesterSpecial = findSpecialBusinessHour(company, today.minusDays(1));
        if (yesterSpecial != null && isContinuedSpecial(yesterSpecial, now)) {
            return true;
        }

        //일반영업 시간 체크
        return isOpenByRegular(company, today, now);
    }

    //넘겨받은 날짜의 특별 영업시간 조회
    private SpecialBusinessHour findSpecialBusinessHour(Company company, LocalDate date) {
        return company.getSpecialBusinessHours().stream()
                .filter(sbh -> sbh.getDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    //특별 영업시간 판단
    private boolean isOpenBySpecial(SpecialBusinessHour sbh, LocalTime now) {
        BusinessStatus status = sbh.getBusinessStatus();

        //명시적 휴무
        if (status == BusinessStatus.CLOSED || status == BusinessStatus.TEMPORARILY_CLOSED) {
            return false;
        }

        LocalTime opened = sbh.getOpenedAt();
        LocalTime closed = sbh.getClosedAt();

        if (opened == null || closed == null) return false;

        //당일
        if (opened.isBefore(closed)) {
            return !now.isBefore(opened) && !now.isAfter(closed);
        }

        //익일
        return now.isAfter(opened) || now.isBefore(closed);
    }

    //전날부터 이어지는 특별영업 판별
    private boolean isContinuedSpecial(SpecialBusinessHour sbh, LocalTime now) {
        LocalTime opened = sbh.getOpenedAt();
        LocalTime closed = sbh.getClosedAt();
        if (opened == null || closed == null) return false;

        return opened.isAfter(closed) && now.isBefore(closed);
    }

    //정기 영업시간 판별
    private boolean isOpenByRegular(Company company, LocalDate today, LocalTime now) {
        WeekDay todayDay = WeekDay.fromLocalDate(today);
        WeekDay yesterDay = WeekDay.fromLocalDate(today.minusDays(1));

        for (BusinessHour bh : company.getBusinessHours()) {
            LocalTime opened = bh.getOpenedAt();
            LocalTime closed = bh.getClosedAt();
            if (opened == null || closed == null) continue;

            // 당일 영업
            if (opened.isBefore(closed)) {
                if (bh.getDay().equals(todayDay) && !now.isBefore(opened) && !now.isAfter(closed)) {
                    return true;
                }
            }
            // 익일 영업
            else {
                if (bh.getDay().equals(todayDay) && now.isAfter(opened)) return true;
                if (bh.getDay().equals(yesterDay) && now.isBefore(closed)) return true;
            }
        }
        return false;
    }
}
