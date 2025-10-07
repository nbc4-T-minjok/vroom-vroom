package com.sparta.vroomvroom.domain.review.service;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.repository.OrderRepository;
import com.sparta.vroomvroom.domain.review.model.dto.request.OwnerReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.request.ReviewRequestDto;
import com.sparta.vroomvroom.domain.review.model.dto.response.ReviewResponseDto;
import com.sparta.vroomvroom.domain.review.model.entity.OwnerReview;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.review.repository.OwnerReviewRepository;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        review.setUserId(user);
        review.setCompany(company);
        review.setRate(requestDto.getRate());
        review.setContents(requestDto.getReviewContents());

        //리뷰 저장
        reviewRepository.save(review);

    }

    public void createReviewCompany(UUID reviewId, @Valid OwnerReviewRequestDto requestDto) {
        //review, user 엔티티 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalArgumentException("유효하지 않은 리뷰 ID입니다."));

        OwnerReview ownerReview = new OwnerReview();
        ownerReview.setReview(review);
        ownerReview.setContents(requestDto.getContents());
        // CreatedBy 없는동안 임시
        ownerReview.setCreatedBy("tester");

        // 리뷰 저장_업체
        ownerReviewRepository.save(ownerReview);

    }

    public List<ReviewResponseDto> getReviewList(Long userId, int page, int size, String sort) {
        // 1. Pageabel 객체 생성 (정렬 기준 : sort)
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).descending());

        // 2. Repository에서 userId로 페이징&정렬하여 리뷰 목록 조회
        Page<Review> reviewPage = reviewRepository.findByUserId_UserId(userId, pageable);

        // 3. Entity -> DTO 반환
        return reviewPage.stream()
                .map(review -> new ReviewResponseDto(review))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getReviewListCompany(UUID compId, int page, int size, String sort) {
        // 1. Pageabel 객체 생성 (정렬 기준 : sort)
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).descending());

        // 2. Repository에서 userId로 페이징&정렬하여 리뷰 목록 조회
        Page<Review> reviewPage = reviewRepository.findByCompany_CompanyId(compId, pageable);

        // 3. Entity -> DTO 반환
        return reviewPage.stream()
                .map(review -> new ReviewResponseDto(review))
                .collect(Collectors.toList());

    }

    public List<ReviewResponseDto> getReview(Long userId, UUID reviewId) {
        List<Review> reviewResult = reviewRepository.findByUserId_UserIdAndId(userId,reviewId);
        return reviewResult.stream()
                .map(review -> new ReviewResponseDto(review))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getReviewCompany(UUID compId, UUID reviewId) {
        List<Review> reviewResult = reviewRepository.findByCompany_CompanyIdAndId(compId,reviewId);
        return reviewResult.stream()
                .map(review -> new ReviewResponseDto(review))
                .collect(Collectors.toList());
    }

    public void updateReview(UUID reviewId, Long userId, @Valid ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        // 권한 체크
        if (!review.getUserId().getUserId().equals(userId)) {
            throw new RuntimeException("리뷰 수정 권한이 없습니다.");
        }

        // 리뷰 내용 변경
        review.setRate(requestDto.getRate());
        review.setContents(requestDto.getReviewContents());
    }

    public void updateReviewCompany(UUID compId, UUID ownerReviewId, Long userId, @Valid OwnerReviewRequestDto requestDto) {
        OwnerReview ownerReview = ownerReviewRepository.findById(ownerReviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        //권한 체크


        //리뷰 내용 변경
        ownerReview.setContents(requestDto.getContents());
    }

    public void deleteReview(UUID reviewId, Long userId) {
        // 1. 리뷰 조회 및 논리삭제
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));
        if (review != null) {
            review.softDelete(LocalDateTime.now(), String.valueOf(userId));
        }

        // 2. 연관된 사장님리뷰 조회 및 논리삭제 처리
        OwnerReview ownerReview = ownerReviewRepository.findByReviewId(reviewId); // 또는 findByOrderId(...) 등 엔티티 연결고리에 따라 수정
        if (ownerReview != null) {
            ownerReview.softDelete(LocalDateTime.now(), String.valueOf(userId));
        }
    }

    public void deleteReviewCompany(UUID reviewId, Long userId) {
        // 리뷰 존재 여부 확인
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        // 사장님 리뷰만 조회 후 삭제 처리
        OwnerReview ownerReview = ownerReviewRepository.findByReviewId(reviewId); // 또는 findByOrderId(...) 등 엔티티 연결고리에 따라 수정
        if (ownerReview != null) {
            ownerReview.softDelete(LocalDateTime.now(), String.valueOf(userId));
        }
    }
}
