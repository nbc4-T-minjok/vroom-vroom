package com.sparta.vroomvroom.domain.cart.service;

import com.sparta.vroomvroom.domain.cart.model.dto.request.AddCartMenuRequest;
import com.sparta.vroomvroom.domain.cart.model.dto.request.UpdateCartMenuRequest;
import com.sparta.vroomvroom.domain.cart.model.dto.response.CartMenuResponse;
import com.sparta.vroomvroom.domain.cart.model.dto.response.CartResponse;
import com.sparta.vroomvroom.domain.cart.model.entity.Cart;
import com.sparta.vroomvroom.domain.cart.model.entity.CartMenu;
import com.sparta.vroomvroom.domain.cart.repository.CartMenuRepository;
import com.sparta.vroomvroom.domain.cart.repository.CartRepository;
import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.menu.model.entity.Menu;
import com.sparta.vroomvroom.domain.menu.repository.MenuRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMenuRepository cartMenuRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public CartResponse getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartMenu> cartMenus = cartMenuRepository.findByCart_CartId(cart.getCartId());

        return buildCartResponse(cart, cartMenus);
    }

    @Transactional
    public void createCartMenu(Long userId, AddCartMenuRequest request) {
        Cart cart = getOrCreateCart(userId);
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));

        // 장바구니에 이미 다른 가게의 메뉴가 있는지 확인
        List<CartMenu> existingCartMenus = cartMenuRepository.findByCart_CartId(cart.getCartId());
        if (!existingCartMenus.isEmpty()) {
            UUID existingCompanyId = existingCartMenus.get(0).getMenu().getCompanyId();
            if (!existingCompanyId.equals(menu.getCompanyId())) {
                throw new IllegalArgumentException("다른 가게의 메뉴는 함께 담을 수 없습니다. 장바구니를 비운 후 다시 시도해주세요.");
            }
        }

        // 이미 장바구니에 같은 메뉴가 있으면 수량 증가
        Optional<CartMenu> existingCartMenu = cartMenuRepository.findByCart_CartIdAndMenu_MenuId(
                cart.getCartId(), request.getMenuId());

        if (existingCartMenu.isPresent()) {
            CartMenu cartMenu = existingCartMenu.get();
            cartMenu.increaseAmount(request.getMenuAmount());
            cartMenuRepository.save(cartMenu);
        } else {
            CartMenu newCartMenu = CartMenu.createCartMenu(cart, menu, request.getMenuAmount(), cart.getUser().getUserName());
            cartMenuRepository.save(newCartMenu);
        }

        // 장바구니 상태 업데이트
        cart.markAsNotEmpty();
    }

    @Transactional
    public void updateCartMenu(Long userId, UUID cartMenuId, UpdateCartMenuRequest request) {
        Cart cart = getOrCreateCart(userId);
        CartMenu cartMenu = cartMenuRepository.findById(cartMenuId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 메뉴를 찾을 수 없습니다."));

        // 권한 확인
        if (!cartMenu.isSameCart(cart.getCartId())) {
            throw new IllegalArgumentException("해당 장바구니 메뉴에 대한 권한이 없습니다.");
        }

        // 수량이 0이면 삭제
        if (request.getMenuAmount() == 0) {
            cartMenuRepository.delete(cartMenu);
        } else {
            cartMenu.updateAmount(request.getMenuAmount());
            cartMenuRepository.save(cartMenu);
        }

        // 장바구니 상태 업데이트
        boolean hasItems = cartMenuRepository.existsByCart_CartId(cart.getCartId());
        cart.updateEmptyStatus(hasItems);
    }

    @Transactional
    public void deleteCartMenu(Long userId, UUID cartMenuId) {
        Cart cart = getOrCreateCart(userId);
        CartMenu cartMenu = cartMenuRepository.findById(cartMenuId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 메뉴를 찾을 수 없습니다."));

        // 권한 확인
        if (!cartMenu.isSameCart(cart.getCartId())) {
            throw new IllegalArgumentException("해당 장바구니 메뉴에 대한 권한이 없습니다.");
        }

        cartMenuRepository.delete(cartMenu);

        // 장바구니 상태 업데이트
        boolean hasItems = cartMenuRepository.existsByCart_CartId(cart.getCartId());
        cart.updateEmptyStatus(hasItems);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartMenu> cartMenus = cartMenuRepository.findByCart_CartId(cart.getCartId());

        cartMenuRepository.deleteAll(cartMenus);

        cart.markAsEmpty();
    }


    // 유저의 장바구니 조회 또는 생성
    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUser_UserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

                    Cart newCart = Cart.createCart(user);
                    return cartRepository.save(newCart);
                });
    }

    private CartResponse buildCartResponse(Cart cart, List<CartMenu> cartMenus) {
        int totalPrice = 0;
        List<CartMenuResponse> cartMenuResponses = new ArrayList<>();

        for (CartMenu cartMenu : cartMenus) {
            Menu menu = cartMenu.getMenu();

            Company company = companyRepository.findById(menu.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다."));

            totalPrice += menu.getMenuPrice() * cartMenu.getMenuAmount();

            CartMenuResponse menuResponse = new CartMenuResponse(
                    cartMenu.getCartMenuId(),
                    menu.getMenuId(),
                    menu.getMenuName(),
                    menu.getMenuPrice(),
                    cartMenu.getMenuAmount(),
                    menu.getMenuImage(),
                    company.getCompanyId(),
                    company.getCompanyName()
            );
            cartMenuResponses.add(menuResponse);
        }

        return new CartResponse(
                cart.getCartId(),
                cart.isEmpty(),
                totalPrice,
                cartMenuResponses
        );
    }
}

/*
    TODO: Menu 도메인 개발 완료 후 아래 테스트 데이터 삭제
    -- 1. CompanyCategory 테스트 데이터
    INSERT INTO company_categories (
        company_category_id,
        company_category_name,
        created_at,
        created_by,
        is_deleted
    ) VALUES (
                 '00000000-0000-0000-0000-000000000200',
                 '치킨',
                 NOW(),
                 'system',
                 false
             )
    ON CONFLICT (company_category_id) DO NOTHING;

    -- 2. Company 테스트 데이터
    INSERT INTO companies (
        company_id,
        company_category_id,
        company_name,
        company_logo_url,
        company_description,
        phone_number,
        delivery_fee,
        delivery_radius,
        owner_name,
        biz_reg_no,
        address,
        detail_address,
        zip_code,
        created_at,
        created_by,
        is_deleted
    ) VALUES (
                 '00000000-0000-0000-0000-000000000100',
                 '00000000-0000-0000-0000-000000000200',
                 '테스트 치킨집',
                 'http://example.com/logo.png',
                 '맛있는 치킨집',
                 '02-1234-5678',
                 3000,
                 5000,
                 '홍길동',
                 '123-45-67890',
                 '서울시 강남구',
                 '테헤란로 123',
                 '06000',
                 NOW(),
                 'system',
                 false
             )
    ON CONFLICT (company_id) DO NOTHING;

    -- 3. Menu 테스트 데이터 (3개)
    INSERT INTO menus (
        menu_id,
        company_id,
        name,
        price,
        menu_group,
        menu_image,
        menu_description,
        menu_status,
        is_visible,
        created_at,
        created_by
    ) VALUES
          (
              '00000000-0000-0000-0000-000000000001',
              '00000000-0000-0000-0000-000000000100',
              '후라이드 치킨',
              18000,
              '메인',
              'https://example.com/fried-chicken.jpg',
              '바삭한 후라이드 치킨',
              'AVAILABLE',
              true,
              NOW(),
              'system'
          ),
          (
              '00000000-0000-0000-0000-000000000002',
              '00000000-0000-0000-0000-000000000101',
              '양념 치킨',
              19000,
              '메인',
              'https://example.com/yangnyeom-chicken.jpg',
              '달콤한 양념 치킨',
              'AVAILABLE',
              true,
              NOW(),
              'system'
          ),
          (
              '00000000-0000-0000-0000-000000000003',
              '00000000-0000-0000-0000-000000000101',
              '콜라',
              2000,
              '사이드',
              'https://example.com/cola.jpg',
              '시원한 콜라',
              'AVAILABLE',
              true,
              NOW(),
              'system'
          )
    ON CONFLICT (menu_id) DO NOTHING;
 */