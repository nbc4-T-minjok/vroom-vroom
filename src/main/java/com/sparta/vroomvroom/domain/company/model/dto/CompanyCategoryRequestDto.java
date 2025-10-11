package com.sparta.vroomvroom.domain.company.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CompanyCategoryRequestDto {

    @NotBlank(message = "카테고리 이름은 공백일 수 없습니다.")
    @Size(max = 20, message = "카테고리 이름은 최대 20자 내외입니다.")
    private String companyCategoryName;
}
