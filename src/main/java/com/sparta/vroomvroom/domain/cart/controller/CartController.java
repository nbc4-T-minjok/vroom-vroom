package com.sparta.vroomvroom.domain.cart.controller;

import com.sparta.vroomvroom.domain.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
}
