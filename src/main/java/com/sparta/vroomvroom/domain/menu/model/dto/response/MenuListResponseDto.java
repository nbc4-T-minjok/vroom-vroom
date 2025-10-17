package com.sparta.vroomvroom.domain.menu.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class MenuListResponseDto {
    private UUID companyId;
    private List<MenuResponseDto> menus;
}

