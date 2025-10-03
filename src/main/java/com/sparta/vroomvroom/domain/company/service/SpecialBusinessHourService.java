package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.repository.SpecialBusinessHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialBusinessHourService {
    private final SpecialBusinessHourRepository specialBusinessHourRepository;
}
