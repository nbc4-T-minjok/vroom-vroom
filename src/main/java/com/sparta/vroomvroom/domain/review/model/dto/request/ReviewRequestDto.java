package com.sparta.vroomvroom.domain.review.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequestDto {
    private UUID compId;

    @Min(1)
    @Max(5)
    private int rate;

    @NotBlank(message = "리뷰는 공백일 수 업습니다.")
    @Pattern(regexp = "^(?=\\s*\\S).{10,}$", message = "리뷰는 10자 이상 입력해주세요")
    private String reviewContents;
//    private List<String> urls = new ArrayList<>();


}
