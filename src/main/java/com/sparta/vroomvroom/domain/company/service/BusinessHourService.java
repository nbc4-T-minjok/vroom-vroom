package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.repository.BusinessHourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessHourService {
    private final BusinessHourRepository businessHourRepository;
}
