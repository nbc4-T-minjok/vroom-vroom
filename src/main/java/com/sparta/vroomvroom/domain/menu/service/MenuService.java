package com.sparta.vroomvroom.domain.menu.service;

import com.sparta.vroomvroom.domain.ai.service.GeminiService;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.menu.model.dto.response.MenuResponseDto;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CompanyRepository companyRepository;
    private final GeminiService geminiService;

    @Transactional
    public void createMenu(UUID companyId, MenuRequestDto requestDto) {
        Company company = findCompany(companyId);

        String aiDescription = requestDto.getMenuDescription();

        if (Boolean.TRUE.equals(requestDto.getAiDescription())) {
            aiDescription = geminiService.generateMenuDescription(
                    requestDto.getMenuName(),
                    requestDto.getMenuPrice()
            );
        }

        Menu menu = new Menu(
                company.getCompanyId(),
                requestDto.getMenuName(),
                requestDto.getMenuGroup(),
                requestDto.getMenuPrice(),
                requestDto.getMenuImage(),
                aiDescription, // AI 결과 반영
                requestDto.getMenuStatus(),
                requestDto.getIsVisible()
        );


        menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(UUID menuId) {
        Menu menu = findMenu(menuId);

        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getMenus(UUID companyId, boolean includeHidden) {
        List<Menu> menu = includeHidden
                ? menuRepository.findAllByCompanyIdAndIsDeletedFalse(companyId)
                : menuRepository.findAllByCompanyIdAndIsDeletedFalseAndIsVisibleTrue(companyId);

        return menu.stream()
                .map(MenuResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuResponseDto updateMenu(UUID menuId, MenuRequestDto requestDto) {
        Menu menu = findMenu(menuId);

        menu.updateMenu(requestDto);
        return new MenuResponseDto(menu);
    }

    @Transactional
    public void deleteMenu(UUID menuId, String deletedBy) {
        Menu menu = findMenu(menuId);

        menu.softDelete(LocalDateTime.now(), deletedBy != null ? deletedBy : "SYSTEM");
    }

    //예외처리
    private Company findCompany(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체를 찾을 수 없습니다. " + companyId));
    }

    private Menu findMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다. " + menuId));
    }

}
