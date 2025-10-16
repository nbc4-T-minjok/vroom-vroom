package com.sparta.vroomvroom.domain.user.controller;

import com.sparta.vroomvroom.domain.user.model.dto.request.UserChangePasswordRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserLoginRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserSignupRequest;
import com.sparta.vroomvroom.domain.user.model.dto.request.UserUpdatedRequest;
import com.sparta.vroomvroom.domain.user.model.dto.response.UserDetailResponse;
import com.sparta.vroomvroom.domain.user.service.EmailService;
import com.sparta.vroomvroom.domain.user.service.UserService;
import com.sparta.vroomvroom.global.conmon.BaseResponse;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerDescription;
import com.sparta.vroomvroom.global.conmon.swagger.SwaggerExamples;
import com.sparta.vroomvroom.global.security.UserDetailsImpl;
import com.sparta.vroomvroom.global.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/v1/users/login")
    @Operation(summary = "로그인 API", description = SwaggerDescription.USER_LOGIN_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.USER_LOGIN_REQUEST)
                            }
                    )
            ))
    public ResponseEntity<Void> swaggerLogin(
            @RequestBody UserLoginRequest loginRequest
    ) {
        // 실제 로직은 필터에서 처리되므로 아무것도 안 함
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/v1/users/logout")
    @Operation(summary = "로그아웃 (Swagger용)", description = "실제 인증은 SecurityFilter에서 처리")
    public ResponseEntity<Void> swaggerLogout(
    ) {
        // 실제 로직은 필터에서 처리되므로 아무것도 안 함
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    //회원 가입
    @PostMapping("/v1/users/signup")
    @Operation(summary = "회원가입 API", description = SwaggerDescription.USER_SIGNUP_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.USER_SIGNUP_REQUEST)
                            }
                    )
            ))
    public BaseResponse signup(
            @Valid @RequestBody UserSignupRequest req
            ) {
        userService.signup(req);
        return new BaseResponse();
    }

    //회원 정보 조회
    @GetMapping("/v1/users")
    @Operation(summary = "회원 정보 조회 API", description = "로그인한 회원의 정보를 조회해오는 API입니다. 로그인 후 이용 가능합니다.")
    public BaseResponse<UserDetailResponse> getUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserDetailResponse res = userService.getUser(userDetails.getUser().getUserName());
        return new BaseResponse(res);
    }

    //회원 정보 수정
    @PatchMapping("/v1/users")
    @PostMapping("/v1/users/signup")
    @Operation(summary = "회원 정보 수정 API", description = SwaggerDescription.USER_DETAIL_UPDATE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.USER_DETAIL_UPDATE_REQUEST)
                            }
                    )
            ))
    public BaseResponse updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserUpdatedRequest userUpdatedRequest
    ){
        //1개 이상의 수정할 필드값이 전달 되었을 때만 비즈니스 로직 호출
        if (!userUpdatedRequest.isEmpty()){
            userService.updateUser(userDetails.getUser().getUserName(),userUpdatedRequest);
        }
        return new BaseResponse();
    }

    //회원 탈퇴
    @DeleteMapping("/v1/users")
    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API 입니다. softDelete 처리 되며 로그인 후 이용 가능합니다.")
    public BaseResponse deleteUser(
            HttpServletRequest req,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        String token = jwtUtil.getTokenFromCookie(req);
        userService.deleteUser(userDetails.getUser().getUserName(),token);
        return new BaseResponse();
    }

    //비밀번호 변경
    @PatchMapping("/v1/users/password")
    @Operation(summary = "비밀번호 변경 API", description = SwaggerDescription.USER_PASSWORD_CHANGE_REQUEST,
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody (
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(value = SwaggerExamples.USER_PASSWORD_CHANGE_REQUEST)
                            }
                    )
            ))
    public BaseResponse changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserChangePasswordRequest req
    ){
        userService.changePassword(userDetails.getUser().getUserName(),req);
        return new BaseResponse();
    }

    @PostMapping("/v2/email/request")
    @Operation(summary = "이메일 인증 요청 API", description = "회원 가입에 사용할 이메일로 인증 메일 발송을 요청합니다.")
    public BaseResponse requestVerification(@RequestParam String email) {
        emailService.sendVerificationMail(email);
        return new BaseResponse();
    }

    @GetMapping("/v2/email/verify")
    public BaseResponse verifyEmail(@RequestParam String email, @RequestParam String code) {
        emailService.verifyEmail(email, code);
        return new BaseResponse();
    }

}
