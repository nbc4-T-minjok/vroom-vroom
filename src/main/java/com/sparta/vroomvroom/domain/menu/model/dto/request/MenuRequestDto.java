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
    private MenuStatus menuStatus;  // "판매중", "품절"

    @NotNull(message = "노출 여부는 필수입니다.")
    private Boolean isVisible;

    private Boolean aiDescription = false;

}
