package com.sparta.vroomvroom.domain.manager.service;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import com.sparta.vroomvroom.domain.company.repository.CompanyRepository;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerCompanyResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerOrderResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerReviewResponse;
import com.sparta.vroomvroom.domain.manager.model.dto.response.ManagerUserResponse;
import com.sparta.vroomvroom.domain.order.model.entity.Order;
import com.sparta.vroomvroom.domain.order.repository.OrderRepository;
import com.sparta.vroomvroom.domain.review.model.entity.Review;
import com.sparta.vroomvroom.domain.review.repository.ReviewRepository;
import com.sparta.vroomvroom.domain.user.model.entity.User;
import com.sparta.vroomvroom.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


    public List<ManagerOrderResponse> getOrders() {
        List<Order> order = orderRepository.findAll();
        List<ManagerOrderResponse> response = order.stream()
                .map(ManagerOrderResponse::new)
                .toList();
        return response;
    }


    public List<ManagerUserResponse> getUsers() {
        List<User> user = userRepository.findAll();
        List<ManagerUserResponse> response = user.stream()
                .map(ManagerUserResponse::new)
                .toList();
        return response;
    }

    public List<ManagerCompanyResponse> getCompanies() {
        List<Company> company = companyRepository.findAll();
        List<ManagerCompanyResponse> response = company.stream()
                .map(ManagerCompanyResponse::new)
                .toList();
        return response;
    }


    public List<ManagerReviewResponse> getReviews() {
        List<Review> review = reviewRepository.findAll();
        List<ManagerReviewResponse> response = review.stream()
                .map(ManagerReviewResponse::new)
                .toList();
        return response;
    }
}
