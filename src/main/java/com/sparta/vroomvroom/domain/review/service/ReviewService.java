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
import com.sparta.vroomvroom.domain.review.model.entity.ReviewImage;
import com.sparta.vroomvroom.domain.review.repository.OwnerReviewRepository;
import com.sparta.vroomvroom.domain.review.repository.ReviewImageRepository;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.constants.OrderStatus;
import com.sparta.vroomvroom.global.conmon.s3.S3Uploader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final OwnerReviewRepository ownerReviewRepository;
    private final S3Uploader s3Uploader;
    private final ReviewImageRepository reviewImageRepository;

    // review 조회
    private Review review(UUID reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalArgumentException("유효하지 않은 리뷰 ID입니다."));
    }

    // Order 조회
    private Order order(UUID orderID){
        return orderRepository.findById(orderID)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 주문 ID입니다."));
    }

    // OwnerReview 조회
    private OwnerReview ownerReview(UUID reviewId){
        return ownerReviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사장님 리뷰입니다."));
    }

    // 데이터 존재 여부 확인
    private <ID, T> void exists(Function<ID, Optional<T>> finder, ID id, boolean isTrue,String errorMessage){
        if(isTrue != (finder.apply(id).isPresent())){               // 데이터가 존재하는지 찾음
            throw new IllegalArgumentException(errorMessage);
        }
    }

    // 리뷰 작성자 일치 여부 확인
    private void checkAuthor(Long writerId, Long userId, String errorMessage){
        if (!userId.equals(writerId)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public void createReview(UUID orderId, Long userId, ReviewRequestDto requestDto, List<MultipartFile> images){
        // order, user, Company 엔티티 조회
        Order order = order(orderId);
        Company company = order.getCompany();
        User user = order.getUser();

        // 리뷰 존재 확인 - orderId 기준
        exists(reviewRepository::findByOrder_OrderId, orderId, false,"이미 해당 주문에 대한 리뷰가 존재합니다.");

        // 주문자 확인
        Long writerId = user.getUserId();
        checkAuthor(writerId, userId, "리뷰 작성은 주문자만 할 수 있습니다.");

        // 주문 상태 확인(배송완료 상태에서만 리뷰 작성 가능)
        OrderStatus state = order.getOrderStatus();
        if (state != OrderStatus.DELIVERY_COMPLETED) {
            throw new IllegalArgumentException("배달 완료 상태에서만 리뷰 작성이 가능합니다.");
        }

        // 리뷰 생성
        Review review = new Review();
        review.setOrder(order);
        review.setUserId(user);
        review.setCompany(company);
        review.setRate(requestDto.getRate());
        review.setContents(requestDto.getContents());

        //리뷰 저장
        reviewRepository.save(review);

        // 이미지 저장
        insertImage(review, images);
    }

    public void createReviewCompany(UUID compId, UUID reviewId, Long userId,@Valid OwnerReviewRequestDto requestDto) {
        //review, compaby 엔티티 조회
        Review review = review(reviewId);

        // 업체 존재 여부 확인
        exists(companyRepository::findById, compId, true,"유효하지 않은 업체입니다.");

        // 사장님 일치 여부 확인
        Long ownerId = review.getCompany().getUser().getUserId();
        checkAuthor(ownerId, userId, "자신의 리뷰만 수정 할 수 있습니다.");

        OwnerReview ownerReview = new OwnerReview();
        ownerReview.setReview(review);
        ownerReview.setContents(requestDto.getContents());

        // 리뷰 저장_업체
        ownerReviewRepository.save(ownerReview);

    }

    // 리뷰 목록 조회_고객
    public List<ReviewResponseDto> getReviewList(Long userId, int page, int size, String sort) {
        // 1. Pageabel 객체 생성 (정렬 기준 : sort)
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).descending());

        // 2. Repository에서 userId로 페이징&정렬하여 리뷰 목록 조회
        Page<Review> reviewPage = reviewRepository.findByUserId_UserId(userId, pageable);

        // 3. Entity -> DTO 반환
        return toDto(reviewPage);
    }

    // 리뷰 목록 조회_업체 기준
    public List<ReviewResponseDto> getReviewListCompany(UUID compId, int page, int size, String sort) {
        // 1. Pageabel 객체 생성 (정렬 기준 : sort)
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).descending());

        // 2. Repository에서 userId로 페이징&정렬하여 리뷰 목록 조회
        Page<Review> reviewPage = reviewRepository.findByCompany_CompanyId(compId, pageable);

        // 3. Entity -> DTO 반환
        return toDto(reviewPage);
    }

    // 리뷰 수정_고객
    public void updateReview(UUID reviewId, Long userId, @Valid ReviewRequestDto requestDto, List<MultipartFile> images) {
        Review review = review(reviewId);

        // 권한 체크
        Long writer = review.getUserId().getUserId();
        checkAuthor(writer, userId, "리뷰 수정 권한이 없습니다.");

        // 리뷰 내용 변경
        review.setRate(requestDto.getRate());
        review.setContents(requestDto.getContents());

        // 기존 이미지 삭제
        if(images != null && !images.isEmpty()) {
            deleteReviewImage(reviewId);
            insertImage(review, images);
        }

    }

    public void updateReviewCompany(UUID compId, UUID reviewId, Long userId, @Valid OwnerReviewRequestDto requestDto) {
        // 리뷰 존재 여부 확인
        exists(reviewRepository::findById, reviewId, true,"유효하지 않은 리뷰입니다.");

        OwnerReview ownerReview = ownerReview(reviewId);

        //권한 체크
        Long writer = Long.valueOf(ownerReview.getCreatedBy());
        checkAuthor(writer, userId, "리뷰 수정 권한이 없습니다.");

        //리뷰 내용 변경
        ownerReview.setContents(requestDto.getContents());
    }

    public void deleteReview(UUID reviewId, Long userId) {
        // 리뷰 존재 여부 확인 및 데이터 세팅
        Review review = review(reviewId);

        // 권한 체크
        Long writer = review.getUserId().getUserId();
        checkAuthor(writer, userId, "리뷰 삭제 권한이 없습니다.");

        // 1. 리뷰 이미지 삭제 처리 (자식 데이터)
        deleteReviewImage(reviewId);

        // 2. 리뷰 논리삭제
        review.softDelete(LocalDateTime.now(), String.valueOf(userId));

        // 3. 연관된 사장님리뷰 조회 및 논리삭제 처리
        OwnerReview ownerReview = ownerReview(reviewId);
        if (ownerReview != null) {
            ownerReview.softDelete(LocalDateTime.now(), String.valueOf(userId));
        }
    }

    public void deleteReviewCompany(UUID reviewId, Long userId) {
        OwnerReview ownerReview = ownerReview(reviewId);

        // 리뷰 존재 여부 확인
        exists(reviewRepository::findById, reviewId, true,"유효하지 않은 리뷰입니다.");

        // 권한 체크
        Long writer = Long.valueOf(ownerReview.getCreatedBy());
        checkAuthor(writer, userId, "사장님 리뷰 삭제 권한이 없습니다.");

        // 사장님 리뷰 논리 삭제
        ownerReview.softDelete(LocalDateTime.now(), String.valueOf(userId));
    }

    // 이미지 삭제
    public void deleteReviewImage(UUID reviewId) {
        List<ReviewImage> images = reviewImageRepository.findAllByReviewId(reviewId);

        if (images == null || images.isEmpty()) return;

        List<String> failed = new ArrayList<>();
        for (ReviewImage image : images) {
            String imageUrl = image.getUrl();

            if (imageUrl != null && !imageUrl.isBlank()) {
                try {
                    // 1. DB 리뷰 삭제
                    reviewImageRepository.deleteByUrl(imageUrl);
                    // 2. S3 이미지 삭제
                    s3Uploader.delete(imageUrl);
                    log.info("파일 삭제 성공, url={}", imageUrl);
                } catch (IllegalArgumentException e) {
                    failed.add(imageUrl);
                    log.error("이미지 파일 삭제 오류 {}: {}", imageUrl, e.getMessage());
                    throw new IllegalArgumentException("S3 파일 삭제 중 오류");
                }
            }
        }
        if (!failed.isEmpty()) {
            log.warn("삭제 실패 이미지 URL: {}", failed);
        }
    }

    // 이미지 추가
    public void insertImage(Review review,List<MultipartFile> images){
        if(images != null && !images.isEmpty()){
            List<String> imageUrls;
            //이미지 저장
            try {
                imageUrls = s3Uploader.uploadFiles(images, "review_images");
            } catch (IOException e) {
                throw new IllegalArgumentException("이미지 업로드 중 에러 발생");
            }

            for(String imagesUrl : imageUrls){
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setReview(review);
                reviewImage.setUrl(imagesUrl);
                reviewImageRepository.save(reviewImage);
            }
        }
    }

    // 리뷰 리스트(Review 엔터티)를 ReviewResposeDto로 일괄 변환
    public List<ReviewResponseDto> toDto(Page<Review> reviewPage){
        return reviewPage.stream()
                .map(review -> {
                    List<String> imageUrls = review.getReviewImages().stream()
                            .map(ReviewImage::getUrl)
                            .collect(Collectors.toList());
                    return new ReviewResponseDto(review, imageUrls);
                })
                .collect(Collectors.toList());

    }
}
