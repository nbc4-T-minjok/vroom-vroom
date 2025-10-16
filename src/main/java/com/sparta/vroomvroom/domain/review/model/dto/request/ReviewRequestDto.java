package com.sparta.vroomvroom.domain.review.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequestDto {
    @Schema(description = "리뷰 평점 (1~5)", example = "5")
    @Min(1)
    @Max(5)
    private Integer rate;

    @Schema(description = "리뷰 본문", example = "음식이 맛있고 배달이 빨라요")
    @NotBlank(message = "리뷰는 공백일 수 업습니다.")
    @Pattern(regexp = "^(?=\\s*\\S).{10,}$", message = "리뷰는 10자 이상 입력해주세요")
    private String contents;

}
