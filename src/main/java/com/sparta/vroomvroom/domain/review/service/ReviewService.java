package com.sparta.vroomvroom.domain.review.service;

import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
}
