package com.sparta.vroomvroom.domain.user.controller;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserUpdatedRequest;
import com.sparta.vroomvroom.domain.user.model.dto.response.UserDetailResponse;
import com.sparta.vroomvroom.domain.user.service.UserService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;


    //회원 가입
    @PostMapping("/users/signup")
    public BaseResponse signup(
            @Valid @RequestBody UserSignupRequest userSignupRequest
            ) {
        userService.signup(userSignupRequest);
        return new BaseResponse();
    }

    //회원 정보 조회
    @GetMapping("/users")
    public BaseResponse<UserDetailResponse> getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserDetailResponse res = userService.getUser(userDetails.getUser().getUserName());
        return new BaseResponse(res);
    }

    //회원 정보 수정
    @PatchMapping("/users")
    public BaseResponse updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserUpdatedRequest userUpdatedRequest
    ){
        userService.updateUser(userDetails.getUser().getUserName(),userUpdatedRequest);
        return new BaseResponse();
    }

    //회원 탈퇴
    @DeleteMapping("/users")
    public BaseResponse deleteUser(
            HttpServletRequest req,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        String token = jwtUtil.getTokenFromCookie(req);
        userService.deleteUser(userDetails.getUser().getUserName(),token);
        return new BaseResponse();
    }

}
