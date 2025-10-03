package com.sparta.vroomvroom.domain.menu.service;

import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
}
