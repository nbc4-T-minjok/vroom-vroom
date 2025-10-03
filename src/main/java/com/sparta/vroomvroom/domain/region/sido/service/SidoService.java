package com.sparta.vroomvroom.domain.region.sido.service;

import com.sparta.vroomvroom.domain.region.sido.repository.SidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SidoService {
    private final SidoRepository sidoRepository;
}
