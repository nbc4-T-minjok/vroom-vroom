package com.sparta.vroomvroom.domain.company.service;

import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
}
