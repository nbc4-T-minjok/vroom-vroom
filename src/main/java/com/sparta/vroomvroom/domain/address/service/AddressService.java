package com.sparta.vroomvroom.domain.address.service;

import com.sparta.vroomvroom.domain.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
}
