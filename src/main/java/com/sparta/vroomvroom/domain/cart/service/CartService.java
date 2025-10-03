package com.sparta.vroomvroom.domain.cart.service;

import com.sparta.vroomvroom.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
}
