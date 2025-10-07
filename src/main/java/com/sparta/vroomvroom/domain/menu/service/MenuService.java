package com.sparta.vroomvroom.domain.menu.service;

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

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final CompanyRepository companyRepository;


    public MenuResponseDto createMenu(UUID companyId, MenuRequestDto requestDto) {
        Company company = findCompany(companyId);

        Menu menu = Menu.builder()
                .company(company)
                .menuName(requestDto.getMenuName())
                .menuGroup(requestDto.getMenuGroup())
                .menuPrice(requestDto.getMenuPrice())
                .menuImage(requestDto.getMenuImage())
                .menuDescription(requestDto.getMenuDescription())
                .menuStatus(requestDto.toMenuStatus())
                .isVisible(requestDto.getIsVisible())
                .build();

        menuRepository.save(menu);
        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(UUID companyId, UUID menuId) {
        Company company = findCompany(companyId);
        Menu menu = findMenu(menuId);

        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getMenus(UUID companyId) {
        List<Menu> menuList = menuRepository.findAllByCompanyIdAndIsDeletedFalse(companyId);
        return menuList.stream()
                .map(MenuResponseDto::new)
                .toList();
    }

    public MenuResponseDto updateMenu(UUID companyId, UUID menuId, MenuRequestDto requestDto) {
        Company company = findCompany(companyId);
        Menu menu = findMenu(menuId);

        menu.updateMenu(
                requestDto.getMenuName(),
                requestDto.getMenuGroup(),
                requestDto.getMenuPrice(),
                requestDto.getMenuImage(),
                requestDto.getMenuDescription(),
                requestDto.toMenuStatus(),
                requestDto.getIsVisible()
        );
        return new MenuResponseDto(menu);
    }

    public MenuResponseDto deleteMenu(UUID companyId, UUID menuId, String deletedBy) {
        Company company = findCompany(companyId);
        Menu menu = findMenu(menuId);

        menu.softDelete(LocalDateTime.now(), deletedBy != null ? deletedBy : "SYSTEM");
        return new MenuResponseDto(menu);
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
