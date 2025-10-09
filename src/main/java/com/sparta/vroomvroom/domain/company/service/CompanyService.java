package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.model.dto.request.CompanyRequestDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyDetailResponseDto;
import com.sparta.vroomvroom.domain.company.model.dto.response.CompanyResponseDto;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.CompanyCategory;
import com.sparta.vroomvroom.domain.company.repository.CompanyCategoryRepository;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyCategoryRepository companyCategoryRepository;
    private final UserRepository userRepository;

    public void createCompany(Long userId, UUID companyCategoryId, CompanyRequestDto requestDto) {
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

        // 위치 정보 검증
        // location에서 lat, lng 꺼내기
        if (requestDto.getLocation() == null) {
            throw new IllegalArgumentException("location 필드는 필수입니다. (lat, lng 포함)");
        }

        Double lat = requestDto.getLocation().getLat(); // 위도
        Double lng = requestDto.getLocation().getLng(); // 경도

        if (lat == null || lng == null) {
            throw new IllegalArgumentException("위도(lat)와 경도(lng)는 모두 필수입니다.");
        }
        if (lat < -90 || lat > 90) {
            throw new IllegalArgumentException("위도(lat)는 -90 ~ 90 사이의 값이어야 합니다.");
        }
        if (lng < -180 || lng > 180) {
            throw new IllegalArgumentException("경도(lng)는 -180 ~ 180 사이의 값이어야 합니다.");
        }

        // 포인트 생성
        GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coord = new Coordinate(lng, lat); // 경도, 위도 순서로 변경 필수
        Point location = geomFactory.createPoint(coord);

        // 업체 생성 및 저장
        Company company = new Company(category, requestDto);
        companyRepository.save(company);
    }

    public CompanyDetailResponseDto getCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
        return CompanyDetailResponseDto.of(company);
    }

    public List<CompanyResponseDto> getCompanies() {
        // 삭제된 업체제외
        // 영업중인 업체만 조회
        // 배송지로부터 배달가능거리
        // 기타 페이징 조회

        List<Company> companies = companyRepository.findAllByIsDeletedFalse();
        return companies.stream().map(CompanyResponseDto::of).toList();
    }

    public List<CompanyResponseDto> getCompaniesByCategory(UUID companyCategoryId) {
        List<Company> companies = companyRepository.findAllByCompanyCategoryIdAndIsDeletedFalse(companyCategoryId);
        return companies.stream().map(CompanyResponseDto::of).toList();
    }

    @Transactional
    public CompanyDetailResponseDto updateCompany(Long userId, UUID companyId, CompanyRequestDto requestDto) {
        // 유저 존재 및 권한 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (user.getRole().equals(UserRole.ROLE_CUSTOMER)) { // 예시: UserRole.OWNER
            throw new IllegalArgumentException("해당 사용자는 권한이 없습니다.");
        }

        // 업체 확인
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 업체 수정
        company.update(requestDto);

        // 감사 로그
        //company.update(user.getUpdatedBy());

        // 엔티티에서 responseDto 로 변환 후 반환
        return CompanyDetailResponseDto.of(company);
    }

    @Transactional
    public void deleteCompany(Long userId, UUID companyId) {
        // 유저 존재 및 권한 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        if (user.getRole().equals(UserRole.ROLE_CUSTOMER)) { // 예시: UserRole.OWNER
            throw new IllegalArgumentException("해당 사용자는 권한이 없습니다.");
        }

        // 업체 확인
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 업체 삭제
        company.softDelete(LocalDateTime.now(), user.getUserName());
    }

}
