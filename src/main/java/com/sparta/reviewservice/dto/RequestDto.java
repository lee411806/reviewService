package com.sparta.reviewservice.dto;


import com.sparta.reviewservice.entity.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class RequestDto {
    private Long userId;

    @Min(1)
    @Max(5)
    private float score;
    private String content;

}
