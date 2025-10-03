package com.sparta.vroomvroom.domain.company.controller;

import com.sparta.vroomvroom.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    

}
