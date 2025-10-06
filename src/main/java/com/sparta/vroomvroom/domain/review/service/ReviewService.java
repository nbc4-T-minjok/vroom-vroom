package com.sparta.vroomvroom.domain.review.service;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.repository.OrderRepository;
import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequsetDto;
import com.sparta.vroomvroom.domain.review.model.dto.request.ReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.entity.OwnerReview;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.review.repository.OwnerReviewRepository;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final OwnerReviewRepository ownerReviewRepository;

    public void createReview(UUID orderId, Long userId, ReviewRequestDto requestDto){
        // order, user, Company 엔티티 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 주문 ID입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));
        Company company = companyRepository.findById(requestDto.getCompId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 회사 ID입니다."));
        // 리뷰 존재 확인 - orderId 기준
        reviewRepository.findByOrder(order).ifPresent(entity -> {
            throw new IllegalArgumentException("이미 해당 주문에 대한 리뷰가 존재합니다.");
        });

        Review review = new Review();
        review.setOrder(order);
        review.setUser(user);
        review.setCompany(company);
        review.setRate(requestDto.getRate());
        review.setContents(requestDto.getReviewContents());

        //리뷰 저장
        reviewRepository.save(review);

    }

    public void createReviewCompany(UUID reviewId, @Valid OwnerReviewRequsetDto requestDto) {
        //review, user 엔티티 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalArgumentException("유효하지 않은 리뷰 ID입니다."));

        OwnerReview ownerReview = new OwnerReview();
        ownerReview.setReview(review);
        ownerReview.setContents(requestDto.getOwnerReviewContents());

        // 리뷰 저장_업체
        ownerReviewRepository.save(ownerReview);

    }
}
