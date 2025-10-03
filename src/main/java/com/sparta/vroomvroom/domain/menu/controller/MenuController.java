package com.sparta.vroomvroom.domain.menu.controller;

import com.sparta.vroomvroom.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuservice;
}
