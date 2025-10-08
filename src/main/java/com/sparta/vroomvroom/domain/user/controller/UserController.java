package com.sparta.vroomvroom.domain.user.controller;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.dto.response.UserDetailResponse;
import com.sparta.vroomvroom.domain.user.service.UserService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/signup")
    public BaseResponse signup(
            @Valid @RequestBody UserSignupRequest userSignupRequest
            ) {
        userService.signup(userSignupRequest);
        return new BaseResponse();
    }

    @GetMapping("/users")
    public BaseResponse<UserDetailResponse> getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserDetailResponse res = userService.getUser(userDetails.getUser().getUserId());
        return new BaseResponse(res);
    }
}
