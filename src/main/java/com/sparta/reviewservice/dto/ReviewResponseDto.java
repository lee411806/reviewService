package com.sparta.reviewservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReviewResponseDto {
    private int totalCount;
    private float score;
    private Long cursor;
    private List<ReviewDto> reviews;


}
