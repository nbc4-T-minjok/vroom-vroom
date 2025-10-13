package com.sparta.vroomvroom.domain.order.service;

import com.sparta.vroomvroom.domain.address.model.entity.Address;
import com.sparta.vroomvroom.domain.address.repository.AddressRepository;
import com.sparta.vroomvroom.domain.cart.model.entity.Cart;
import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.cart.repository.CartMenuRepository;
import com.sparta.vroomvroom.domain.cart.repository.CartRepository;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import com.sparta.vroomvroom.domain.order.model.dto.request.CancelOrderRequest;
import com.sparta.vroomvroom.domain.order.model.dto.request.CreateOrderRequest;
import com.sparta.vroomvroom.domain.order.model.dto.request.UpdateOrderStatusRequest;
import com.sparta.vroomvroom.domain.order.model.dto.response.CompanyOrderListResponse;
import com.sparta.vroomvroom.domain.order.model.dto.response.OrderDetailResponse;
import com.sparta.vroomvroom.domain.order.model.dto.response.OrderListResponse;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.model.entity.OrderMenu;
import com.sparta.vroomvroom.domain.order.repository.OrderMenuRepository;
import com.sparta.vroomvroom.domain.order.repository.OrderRepository;
import com.sparta.vroomvroom.domain.payments.model.entity.Payment;
import com.sparta.vroomvroom.domain.payments.repository.PaymentRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.global.conmon.constants.OrderStatus;
import com.sparta.vroomvroom.global.conmon.constants.PaymentMethod;
import com.sparta.vroomvroom.global.conmon.constants.MenuStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PaymentRepository paymentRepository;
    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;

    @Transactional
    public void createOrder(User user, CreateOrderRequest request) {
        // 1. 장바구니 조회
        Cart cart = cartRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));

        List<CartMenu> cartMenus = cartMenuRepository.findByCart_CartId(cart.getCartId());

        if (cartMenus.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어있습니다.");
        }

        // 2. 배송지 존재 및 권한 확인
        Address address = addressRepository.findById(request.getUserAddressId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다."));

        if (!address.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("존재하지 않는 배송지입니다.");
        }

        // 3. 장바구니 메뉴 검증 및 총 금액 계산
        List<Menu> menus = new ArrayList<>();
        int totalPrice = 0;
        Company company = null;

        for (CartMenu cartMenu : cartMenus) {
            Menu menu = cartMenu.getMenu();

            // 메뉴 품절 확인
            if (menu.getMenuStatus() != MenuStatus.AVAILABLE) {
                throw new IllegalArgumentException("품절된 메뉴가 포함되어 있습니다.");
            }

            // 첫 번째 메뉴로 업체 확인
            if (company == null) {
                company = companyRepository.findById(menu.getCompany().getCompanyId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));
            }

            // 모든 메뉴가 같은 업체인지 확인
            if (!menu.getCompany().getCompanyId().equals(company.getCompanyId())) {
                throw new IllegalArgumentException("다른 업체의 메뉴가 포함되어 있습니다.");
            }

            menus.add(menu);
            totalPrice += menu.getMenuPrice() * cartMenu.getMenuAmount();
        }

        // 4. Order 생성
        Order order = Order.createOrder(
                user,
                address,
                company,
                totalPrice,
                request.getOrderRequest(),
                user.getUserName()
        );
        Order savedOrder = orderRepository.save(order);

        // 5. OrderMenu 생성
        for (int i = 0; i < cartMenus.size(); i++) {
            CartMenu cartMenu = cartMenus.get(i);
            Menu menu = menus.get(i);

            OrderMenu orderMenu = OrderMenu.createOrderMenu(
                    savedOrder,
                    menu,
                    cartMenu.getMenuAmount(),
                    menu.getMenuPrice(),
                    user.getUserName()
            );
            orderMenuRepository.save(orderMenu);
        }

        // 6. Payment 생성
        Payment payment = Payment.createPayment(
                savedOrder,
                PaymentMethod.valueOf(request.getPaymentMethod()),
                totalPrice,
                user.getUserName()
        );
        paymentRepository.save(payment);

        // 7. 주문 완료 후 장바구니 비우기
        cartMenuRepository.deleteAll(cartMenus);
        cart.markAsEmpty();
    }


    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(User user, UUID orderId) {
        // 1. 주문 조회 + 권한 확인
        Order order = getOrderWithPermission(orderId, user);

        // 2. 주문 메뉴 조회
        List<OrderMenu> orderMenus = orderMenuRepository.findByOrder_OrderId(orderId);

        // 3. Response 생성
        return OrderDetailResponse.from(order, orderMenus);
    }

    @Transactional(readOnly = true)
    public OrderListResponse getOrders(User user, Integer page,  Integer size, String orderStatus ) {
        // 1. 페이지 유효성 검증
        if (page < 1) {
            throw new IllegalArgumentException("잘못된 페이지 번호입니다.");
        }

        // 2. Pageable 생성 (0-based)
        Pageable pageable = PageRequest.of(page - 1, size);

        // 3. 주문 조회
        Page<Order> orderPage;

        if (orderStatus != null && !orderStatus.isEmpty()) {
            // 주문 상태 필터링
            OrderStatus status = validateAndGetOrderStatus(orderStatus);
            orderPage = orderRepository.findByUser_UserIdAndOrderStatusOrderByCreatedAtDesc(
                    user.getUserId(),
                    status,
                    pageable
            );
        } else {
            // 전체 주문 조회
            orderPage = orderRepository.findByUser_UserIdOrderByCreatedAtDesc(
                    user.getUserId(),
                    pageable
            );
        }

        // 4. DTO 변환
        List<OrderListResponse.OrderSummaryResponse> orders = orderPage.getContent()
                .stream()
                .map(OrderListResponse.OrderSummaryResponse::from)
                .collect(Collectors.toList());

        return OrderListResponse.from(orderPage, orders);
    }


    @Transactional
    public void cancelOrder(User user, UUID orderId, CancelOrderRequest request) {
        // 1. 주문 조회 + 권한 확인
        Order order = getOrderWithPermission(orderId, user);

        // 2. 이미 취소된 주문인지 확인
        if (order.getOrderStatus() == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        // 3. 취소 가능한 상태인지 확인
        if (order.getOrderStatus() != OrderStatus.PENDING &&
                order.getOrderStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalArgumentException("조리 완료 이후 주문은 취소할 수 없습니다.");
        }

        // 4. 주문 취소
        order.cancel(request.getCancelReason(), user.getUserName());

        // 5. 결제 환불 처리
        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));

        payment.refund(user.getUserName());
    }

    @Transactional
    public void updateOrderStatus(User user, UUID orderId, UpdateOrderStatusRequest request) {
        // 1. 주문 조회
        Order order = getOrder(orderId);

        // 2. 이미 취소된 주문인지 확인
        if (order.getOrderStatus() == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소된 주문입니다.");
        }

        // 3. 유효한 주문 상태인지 확인
        OrderStatus newStatus = validateAndGetOrderStatus(request.getOrderStatus());

        // 4. CANCELED로 변경 시도 방지
        if (newStatus == OrderStatus.CANCELED) {
            throw new IllegalArgumentException("현재 상태에서 변경할 수 없습니다.");
        }

        // 5. 권한 확인 (업체 주인만)
        // TODO: 업체 주문인지 체크 필요 현재 연관관계 X

        // 6. 주문 상태 변경
        order.updateStatus(newStatus, user.getUserName());
    }

    @Transactional(readOnly = true)
    public CompanyOrderListResponse getCompanyOrders(
            User user,
            UUID companyId,
            Integer page,
            Integer size,
            String orderStatus,
            LocalDate startDate,
            LocalDate endDate
    ) {
        // 1. 업체 존재 확인
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업체입니다."));

        // 2. 권한 확인
        // TODO: 업체 주인인지 체크 필요 (현재 User-Company 연관관계 X)

        // 3. 페이지 유효성 검증
        if (page < 1) {
            throw new IllegalArgumentException("잘못된 페이지 번호입니다.");
        }

        // 4. Pageable 생성
        Pageable pageable = PageRequest.of(page - 1, size);

        // 5. 날짜 범위를 LocalDateTime으로 변환
        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        // 6. 주문 조회 (필터링 조합)
        Page<Order> orderPage;

        if (orderStatus != null && !orderStatus.isEmpty() && startDateTime != null && endDateTime != null) {
            // 상태 + 날짜 필터링
            OrderStatus status = validateAndGetOrderStatus(orderStatus);
            orderPage = orderRepository.findByCompany_CompanyIdAndOrderStatusAndCreatedAtBetweenOrderByCreatedAtDesc(
                    companyId, status, startDateTime, endDateTime, pageable
            );
        } else if (orderStatus != null && !orderStatus.isEmpty()) {
            // 상태만 필터링
            OrderStatus status = validateAndGetOrderStatus(orderStatus);
            orderPage = orderRepository.findByCompany_CompanyIdAndOrderStatusOrderByCreatedAtDesc(
                    companyId, status, pageable
            );
        } else if (startDateTime != null && endDateTime != null) {
            // 날짜만 필터링
            orderPage = orderRepository.findByCompany_CompanyIdAndCreatedAtBetweenOrderByCreatedAtDesc(
                    companyId, startDateTime, endDateTime, pageable
            );
        } else {
            // 필터링 없음
            orderPage = orderRepository.findByCompany_CompanyIdOrderByCreatedAtDesc(
                    companyId, pageable
            );
        }

        // 7. DTO 변환
        List<CompanyOrderListResponse.CompanyOrderSummaryResponse> orders = orderPage.getContent()
                .stream()
                .map(CompanyOrderListResponse.CompanyOrderSummaryResponse::from)
                .collect(Collectors.toList());

        return CompanyOrderListResponse.from(orderPage, orders);
    }

    // Private 메서드들
    /**
     * 주문 조회 (권한 확인 없음)
     */
    private Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
    }

    /**
     * 주문 조회 + 권한 확인 (고객 본인 확인)
     */
    private Order getOrderWithPermission(UUID orderId, User user) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        if (!order.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("해당 주문에 대한 권한이 없습니다.");
        }

        return order;
    }

    /**
     * OrderStatus 문자열 검증 및 Enum 변환
     */
    private OrderStatus validateAndGetOrderStatus(String orderStatus) {
        try {
            return OrderStatus.valueOf(orderStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 주문 상태입니다.");
        }
    }
}