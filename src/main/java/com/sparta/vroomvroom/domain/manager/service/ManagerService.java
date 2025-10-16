package com.sparta.vroomvroom.domain.manager.service;

import com.sparta.vroomvroom.domain.address.model.entity.Address;
import com.sparta.vroomvroom.domain.address.repository.AddressRepository;
import com.sparta.vroomvroom.domain.cart.model.entity.Cart;
import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.cart.repository.CartMenuRepository;
import com.sparta.vroomvroom.domain.cart.repository.CartRepository;
import com.sparta.vroomvroom.domain.company.model.entity.BusinessHour;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.model.entity.SpecialBusinessHour;
import com.sparta.vroomvroom.domain.company.repository.BusinessHourRepository;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.company.repository.SpecialBusinessHourRepository;
import com.sparta.vroomvroom.domain.manager.model.dto.request.ManagerRegisterRequest;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerCompanyResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerOrderResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerReviewResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerUserResponse;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.repository.OrderRepository;
import com.sparta.vroomvroom.domain.payments.model.entity.Payment;
import com.sparta.vroomvroom.domain.payments.repository.PaymentRepository;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import com.sparta.vroomvroom.global.conmon.constants.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final PaymentRepository paymentRepository;
    private final AddressRepository addressRepository;
    private final BusinessHourRepository businessHourRepository;
    private final SpecialBusinessHourRepository specialBusinessHourRepository;
    private final PasswordEncoder passwordEncoder;


    public List<ManagerOrderResponse> getOrders() {
        List<Order> order = orderRepository.findAllByIsDeletedFalse();
        return order.stream()
                .map(ManagerOrderResponse::new)
                .toList();
    }


    public List<ManagerUserResponse> getUsers() {
        List<User> user = userRepository.findAllByIsDeletedFalse();
        return user.stream()
                .map(ManagerUserResponse::new)
                .toList();
    }

    public List<ManagerCompanyResponse> getCompanies() {
        List<Company> company = companyRepository.findAllByIsDeletedFalse();
        return company.stream()
                .map(ManagerCompanyResponse::new)
                .toList();
    }


    public List<ManagerReviewResponse> getReviews() {
        List<Review> review = reviewRepository.findAllByIsDeletedFalse();
        return review.stream()
                .map(ManagerReviewResponse::new)
                .toList();
    }

    @Transactional
    public void cancleOrder(UUID orderId) {
        boolean isRefund = false;
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않거나 유효하지 않은 주문입니다.")
        );
        //주문 취소
        order.cancel("관리자에 의한 취소처리", UserRole.ROLE_MANAGER.getRole());
        //환불
        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        payment.refund(UserRole.ROLE_MANAGER.getRole());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
        if(user.getRole() == UserRole.ROLE_MASTER){
            throw new IllegalArgumentException("MASTER는 삭제할 수 없습니다.");
        }
        Optional<Cart> cart = cartRepository.findByUser_UserId(userId);

        Optional<Company> company = companyRepository.findByUser_UserId(userId);
        //회원 삭제
        user.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());

        //업체 존재시 삭제
        if(company.isPresent()){
            deleteCompany(company.get().getCompanyId());
        }

        //장바구니 삭제
        if(cart.isPresent()){
            cart.get().softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());
            //장바구니에 담긴 메뉴 삭제 (물리 삭제)
            List<CartMenu> cartMenus = cartMenuRepository.findAllByCart_CartId(cart.get().getCartId());
            cartMenuRepository.deleteAll(cartMenus);
        }

        //주소 목록 삭제
        List<Address> addresses = addressRepository.findAllByUserId(userId);
        addresses.forEach(address -> {address.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());});


    }

    @Transactional
    public void deleteCompany(UUID companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 업체입니다.")
        );
        company.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());

        List<BusinessHour> businessHours = businessHourRepository.findAllByCompanyId(companyId);
        List<SpecialBusinessHour> specialBusinessHours = specialBusinessHourRepository.findAllByCompany_CompanyIdAndIsDeletedFalse(companyId);
        List<Review> reviews = reviewRepository.findAllByCompany_CompanyId(companyId);
        //영업시간 삭제
        businessHours.forEach(businessHour -> {businessHour.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());});
        //특별 영업시간 삭제
        specialBusinessHours.forEach(specialBusinessHour -> {specialBusinessHour.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());});
        //리뷰 삭제
        reviews.forEach(review -> {
            review.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());
            if(review.getOwnerReview() != null){
                review.getOwnerReview().softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());
            }
        });

    }

    @Transactional
    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 리뷰입니다.")
        );
        //사장님 리뷰 있으면 삭제
        if(review.getOwnerReview() != null){
            review.getOwnerReview().softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());
        }
        //리뷰 삭제
        review.softDelete(LocalDateTime.now(), UserRole.ROLE_MANAGER.getRole());
    }

    public void createManager(ManagerRegisterRequest request) {
        userRepository.findByUserNameOrEmailOrPhoneNumberAndNotDeleted(request.getUserName(), request.getEmail(), request.getPhoneNumber())
                .ifPresent(user -> {
                            throw new IllegalArgumentException("입력한 정보로 이미 가입된 회원이 존재합니다.");
                });
        User user = new User(request, passwordEncoder.encode(request.getPassword()));
        user.create(UserRole.ROLE_MASTER.getRole());
        userRepository.save(user);
    }
}
