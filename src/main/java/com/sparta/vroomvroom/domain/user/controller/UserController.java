package com.sparta.vroomvroom.domain.user.controller;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.service.UserService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/signup")
    public BaseResponse signup(
            @RequestBody UserSignupRequest userSignupRequest
            ) {
        userService.signup();
        return new BaseResponse();
    }
}
