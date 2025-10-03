package com.sparta.vroomvroom.domain.order.controller;

import com.sparta.vroomvroom.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
}
