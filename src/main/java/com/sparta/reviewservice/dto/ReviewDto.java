package com.sparta.reviewservice.dto;

import com.sparta.reviewservice.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long userId;
    private float score;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;


}

