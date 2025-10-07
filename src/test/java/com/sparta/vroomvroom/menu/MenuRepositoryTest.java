package com.sparta.vroomvroom.menu;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuRepositoryMockTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("메뉴 저장(Mock) 테스트")
    void saveMenuWithMockCompany() {
        // given
        Company company = Company.builder()
                .companyId(UUID.randomUUID())
                .companyName("테스트업체")
                .build();

        Menu menu = Menu.builder()
                .company(company)
                .menuName("치킨")
                .menuGroup("치킨")
                .menuPrice(20000)
                .menuDescription("후라이드 치킨")
                .menuStatus(MenuStatus.AVAILABLE)
                .isVisible(true)
                .build();


        given(menuRepository.findAllByCompanyIdAndIsDeletedFalse(company.getCompanyId()))
                .willReturn(List.of(menu));

        // when
        List<Menu> foundMenus = menuRepository.findAllByCompanyIdAndIsDeletedFalse(company.getCompanyId());

        // then
        assertThat(foundMenus).isNotEmpty();
        assertThat(foundMenus.get(0).getMenuName()).isEqualTo("후라이드 치킨");
        assertThat(foundMenus.get(0).getCompanyId()).isEqualTo(company.getCompanyId());
    }
}
