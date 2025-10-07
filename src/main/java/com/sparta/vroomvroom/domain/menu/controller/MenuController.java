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
    public ResponseEntity<BaseResponse<MenuResponseDto>> createMenu(@PathVariable UUID companyId,
                                                                    @RequestBody MenuRequestDto requestDto) {
        MenuResponseDto responseDto = menuservice.createMenu(companyId, requestDto);
        return ResponseEntity.ok(new BaseResponse<>(responseDto));
    }

    @GetMapping("/companies/{companyId}/menus/{menuId}")
    public ResponseEntity<BaseResponse<MenuResponseDto>> getMenu(@PathVariable UUID companyId,
                                                                 @PathVariable UUID menuId) {
        MenuResponseDto responseDto = menuservice.getMenu(companyId, menuId);
        return ResponseEntity.ok(new BaseResponse<>(responseDto));
    }

    @GetMapping("/companies/{companyId}/menus")
    public ResponseEntity<BaseResponse<List<MenuResponseDto>>> getMenus(@PathVariable UUID companyId) {
        List<MenuResponseDto> responseDto = menuservice.getMenus(companyId);
        return ResponseEntity.ok(new BaseResponse<>(responseDto));
    }

    @PatchMapping("/companies/{companyId}/menus/{menuId}")
    public ResponseEntity<BaseResponse<MenuResponseDto>> updateMenu(@PathVariable UUID companyId,
                                                                    @PathVariable UUID menuId,
                                                                    @RequestBody MenuRequestDto requestDto) {
        MenuResponseDto responseDto = menuservice.updateMenu(companyId, menuId, requestDto);
        return ResponseEntity.ok(new BaseResponse<>(responseDto));
    }

    @DeleteMapping("/companies/{companyId}/menus/{menuId}")
    public ResponseEntity<BaseResponse<MenuResponseDto>> deleteMenu(@PathVariable UUID companyId,
                                                                    @PathVariable UUID menuId,
                                                                    @RequestParam(defaultValue = "SYSTEM") String deletedBy) {
        MenuResponseDto responseDto = menuservice.deleteMenu(companyId, menuId, deletedBy);
        return ResponseEntity.ok(new BaseResponse<>(responseDto));
    }

}
