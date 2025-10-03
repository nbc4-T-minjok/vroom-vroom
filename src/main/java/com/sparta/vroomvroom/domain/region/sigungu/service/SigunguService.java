package com.sparta.vroomvroom.domain.region.sigungu.service;

import com.sparta.vroomvroom.domain.region.sigungu.repository.SigunguRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SigunguService {
    private final SigunguRepository sigunguRepository;
}
