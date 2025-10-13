package com.sparta.vroomvroom.domain.menu.controller;

import com.sparta.vroomvroom.domain.menu.model.dto.request.MenuRequestDto;
import com.sparta.vroomvroom.domain.menu.model.dto.response.MenuResponseDto;
import com.sparta.vroomvroom.domain.menu.service.MenuService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuservice;

    @Operation(
            summary = "메뉴 등록 API",
            description = SwaggerDescription.MENU_CREATE_REQUEST,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.MENU_CREATE_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PostMapping("/companies/{companyId}/menus")
    public BaseResponse<Void> createMenu(@PathVariable UUID companyId,
                                         @RequestBody MenuRequestDto requestDto) {

        menuservice.createMenu(companyId, requestDto);
        return new BaseResponse<>("메뉴 등록 완료");
    }

    @Operation(summary = "단일 메뉴 조회 API", description = "메뉴 ID로 특정 메뉴의 상세 정보를 조회합니다.")
    @Secured({"ROLE_CUSTOMER", "ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @GetMapping("/companies/{companyId}/menus/{menuId}")
    public BaseResponse<Void> getMenu(@PathVariable UUID menuId) {

        return new BaseResponse(menuservice.getMenu(menuId));
    }

    @Operation(
            summary = "메뉴 목록 조회 API",
            description = "특정 업체의 전체 메뉴 목록을 조회합니다. includeHidden=true 시 숨김 메뉴도 함께 조회됩니다."
    )
    @Secured({"ROLE_CUSTOMER", "ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @GetMapping("/companies/{companyId}/menus")
    public BaseResponse<List<MenuResponseDto>> getMenus(@PathVariable UUID companyId,
                                                        @RequestParam(defaultValue = "false") boolean includeHidden) {

        return new BaseResponse<>(menuservice.getMenus(companyId, includeHidden));
    }

    @Operation(
            summary = "메뉴 수정 API",
            description = SwaggerDescription.MENU_UPDATE_REQUEST,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.MENU_UPDATE_REQUEST)
                            }
                    )
            )
    )
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PatchMapping("/companies/{companyId}/menus/{menuId}")
    public BaseResponse<MenuResponseDto> updateMenu(@PathVariable UUID companyId,
                                                    @PathVariable UUID menuId,
                                                    @RequestBody MenuRequestDto requestDto) {

        return new BaseResponse<>(menuservice.updateMenu(menuId, requestDto));
    }

    @Operation(
            summary = "메뉴 삭제 API",
            description = "특정 메뉴를 삭제(soft delete)합니다."
    )
    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @DeleteMapping("/companies/{companyId}/menus/{menuId}")
    public BaseResponse<Void> deleteMenu(@PathVariable UUID companyId,
                                                                    @PathVariable UUID menuId,
                                                                    @RequestParam(defaultValue = "SYSTEM") String deletedBy) {
        menuservice.deleteMenu(menuId, deletedBy);
        return new BaseResponse<>("메뉴 삭제 완료");
    }

}
