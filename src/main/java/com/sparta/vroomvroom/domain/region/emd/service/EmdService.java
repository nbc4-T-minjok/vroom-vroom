package com.sparta.vroomvroom.domain.region.emd.service;

import com.sparta.vroomvroom.domain.region.emd.repository.EmdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmdService {
    private EmdRepository emdRepository;
}
