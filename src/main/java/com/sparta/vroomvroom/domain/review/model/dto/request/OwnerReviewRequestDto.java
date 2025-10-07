package com.sparta.vroomvroom.domain.review.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OwnerReviewRequestDto {
//    @NotNull (message = "리뷰 아이디는 필수 값입니다.")
//    private UUID reviewId;
//    @NotBlank(message = "사장님 리뷰는 공백일 수 업습니다.")
    @Pattern(regexp = "^(?=\\s*\\S).{10,}$", message = "리뷰는 10자 이상 입력해주세요")
    private String contents;
}
