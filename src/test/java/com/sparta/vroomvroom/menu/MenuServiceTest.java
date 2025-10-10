package com.sparta.vroomvroom.menu;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.menu.model.dto.response.MenuResponseDto;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import com.sparta.vroomvroom.domain.menu.service.MenuService;
import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional

public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company savedCompany;

    @BeforeEach
    void setup() {
        Company company = Company.builder()
                .companyName("테스트 식당")
                .build();
        savedCompany = companyRepository.save(company);
    }

    @Test
    @DisplayName("메뉴 생성 테스트")
    void createMenu() {
        // given
        MenuRequestDto requestDto = new MenuRequestDto(
                savedCompany.getCompanyId().toString(),
                "치킨",
                "치킨",
                20000,
                "http://example.com/menu.jpg",
                "후라이드 치킨",
                "AVAILABLE",
                true
        );

        // when
        MenuResponseDto responseDto = menuService.createMenu(savedCompany.getCompanyId(), requestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("치킨", responseDto.getMenuName());
        assertEquals(20000, responseDto.getMenuPrice());
    }

    @Test
    @DisplayName("메뉴 상세 조회 테스트")
    void getMenu() {
        // given
        Menu menu = Menu.builder()
                .company(savedCompany)
                .menuName("치킨")
                .menuGroup("치킨")
                .menuPrice(20000)
                .menuImage("http://example.com/menu.jpg")
                .menuDescription("후라이드 치킨")
                .menuStatus(MenuStatus.AVAILABLE)
                .isVisible(true)
                .build();
        menuRepository.save(menu);

        // when
        MenuResponseDto result = menuService.getMenu(savedCompany.getCompanyId(), menu.getMenuId());

        // then
        assertThat(result.getMenuName()).isEqualTo("치킨");
        assertThat(result.getMenuStatus()).isEqualTo("판매중");
    }
}
