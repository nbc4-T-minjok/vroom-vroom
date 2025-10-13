package com.sparta.vroomvroom.domain.menu.controller;

import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.menu.model.dto.response.MenuResponseDto;
import com.sparta.vroomvroom.domain.menu.service.MenuService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuservice;

    @PostMapping("/companies/{companyId}/menus")
    public BaseResponse<Void> createMenu(@PathVariable UUID companyId,
                                         @RequestBody MenuRequestDto requestDto) {

        menuservice.createMenu(companyId, requestDto);
        return new BaseResponse<>("메뉴 등록 완료");
    }

    @GetMapping("/companies/{companyId}/menus/{menuId}")
    public BaseResponse<Void> getMenu(@PathVariable UUID menuId) {

        return new BaseResponse(menuservice.getMenu(menuId));
    }

    @GetMapping("/companies/{companyId}/menus")
    public BaseResponse<List<MenuResponseDto>> getMenus(@PathVariable UUID companyId,
                                                        @RequestParam(defaultValue = "false") boolean includeHidden) {

        return new BaseResponse<>(menuservice.getMenus(companyId, includeHidden));
    }

    @PatchMapping("/companies/{companyId}/menus/{menuId}")
    public BaseResponse<MenuResponseDto> updateMenu(@PathVariable UUID companyId,
                                                    @PathVariable UUID menuId,
                                                    @RequestBody MenuRequestDto requestDto) {

        return new BaseResponse<>(menuservice.updateMenu(menuId, requestDto));
    }

    @DeleteMapping("/companies/{companyId}/menus/{menuId}")
    public BaseResponse<Void> deleteMenu(@PathVariable UUID companyId,
                                                                    @PathVariable UUID menuId,
                                                                    @RequestParam(defaultValue = "SYSTEM") String deletedBy) {
        menuservice.deleteMenu(menuId, deletedBy);
        return new BaseResponse<>("메뉴 삭제 완료");
    }

}
