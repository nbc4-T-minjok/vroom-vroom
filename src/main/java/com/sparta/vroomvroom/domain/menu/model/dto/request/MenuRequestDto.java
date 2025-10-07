package com.sparta.vroomvroom.domain.menu.model.dto.request;

import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRequestDto {

    @NotNull(message = "업체 ID는 필수입니다.")
    private String companyId;

    @NotBlank(message = "메뉴 이름은 필수입니다.")
    private String menuName;

    @NotBlank(message = "메뉴 그룹은 필수입니다.")
    private String menuGroup;

    @NotNull(message = "메뉴 가격은 필수입니다.")
    @Min(value = 1, message = "메뉴 가격은 0보다 커야 합니다.")
    private Integer menuPrice;

    private String menuImage;

    private String menuDescription;

    @NotBlank(message = "메뉴 상태는 필수입니다.")
    private String menuStatus;  // "판매중", "품절"

    @NotNull(message = "노출 여부는 필수입니다.")
    private Boolean isVisible;

    // 편의 생성자 (Dto -> Entity 변환 시 활용)
    public MenuRequestDto(String companyId, String menuName, String menuGroup,
                       Integer menuPrice, String menuImage, String menuDescription,
                       String menuStatus, Boolean isVisible) {
//        this.companyId = companyId;
        this.menuName = menuName;
        this.menuGroup = menuGroup;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
        this.menuDescription = menuDescription;
        this.menuStatus = menuStatus;
        this.isVisible = isVisible;
    }
    // 변환 메서드 추가
    public MenuStatus toMenuStatus() {
        return MenuStatus.valueOf(menuStatus.toUpperCase());
    }
}
