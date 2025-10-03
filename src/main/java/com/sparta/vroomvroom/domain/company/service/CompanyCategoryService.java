package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.repository.CompanyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyCategoryService {
    private final CompanyCategoryRepository companyCategoryRepository;
}
