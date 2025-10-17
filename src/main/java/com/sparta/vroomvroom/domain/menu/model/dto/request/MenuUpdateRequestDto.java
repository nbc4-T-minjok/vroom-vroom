package com.sparta.vroomvroom.domain.menu.model.dto.request;

import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuUpdateRequestDto {

    private String menuName;
    private String menuGroup;
    private Integer menuPrice;
    private String menuDescription;
    private MenuStatus menuStatus;
    private Boolean isVisible;
}
