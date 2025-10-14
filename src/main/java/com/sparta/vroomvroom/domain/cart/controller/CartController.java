package com.sparta.vroomvroom.domain.cart.controller;

import com.sparta.vroomvroom.domain.cart.model.dto.request.AddCartMenuRequest;
import com.sparta.vroomvroom.domain.cart.model.dto.request.UpdateCartMenuRequest;
import com.sparta.vroomvroom.domain.cart.model.dto.response.CartResponse;
import com.sparta.vroomvroom.domain.cart.service.CartService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class

CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니 조회 API")
    @GetMapping("/carts")
    public BaseResponse<CartResponse> getCart(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CartResponse response = cartService.getCart(userDetails.getUser().getUserId());
        return new BaseResponse<>(response);
    }

    @Operation(summary = "장바구니 메뉴 추가", description = SwaggerDescription.CART_ADD_REQUEST,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                content = @Content(
                        mediaType = "application/json",
                        examples = {
                                @ExampleObject(value = SwaggerExamples.CART_ADD_REQUEST)
                        }
                )
            ))
    @PostMapping("/carts")
    public BaseResponse<Void> createCartMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AddCartMenuRequest request
    ) {
        cartService.createCartMenu(userDetails.getUser().getUserId(), request);
        return new BaseResponse<>();
    }

    @Operation(summary = "장바구니 수량 수정 API", description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.CART_UPDATE_REQUEST)
                            }
                    )
            ))
    @PatchMapping("/carts/{cartMenuId}")
    public BaseResponse<Void> updateCartMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID cartMenuId,
            @Valid @RequestBody UpdateCartMenuRequest request
    ) {
        cartService.updateCartMenu(
                userDetails.getUser().getUserId(),
                cartMenuId,
                request
        );
        return new BaseResponse<>();
    }

    @Operation(summary = "장바구니 메뉴 삭제 API")
    @DeleteMapping("/carts/{cartMenuId}")
    public BaseResponse<Void> deleteCartMenu(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable UUID cartMenuId
    ) {
        cartService.deleteCartMenu(
                userDetails.getUser().getUserId(),
                cartMenuId
        );
        return new BaseResponse<>();
    }

    @Operation(summary = "장바구니 전체 삭제 API")
    @DeleteMapping("/carts")
    public BaseResponse<Void> clearCart(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        cartService.clearCart(userDetails.getUser().getUserId());
        return new BaseResponse<>();
    }
}
