package com.sparta.vroomvroom.domain.review.model.dto.request;

import com.sparta.vroomvroom.domain.company.model.entity.Company;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ReviewRequestDto {
    private Company compId;
    private int rate;
    private String reviewContents;
//    private List<String> urls = new ArrayList<>();



}
