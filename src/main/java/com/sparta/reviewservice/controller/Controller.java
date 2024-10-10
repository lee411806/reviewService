package com.sparta.reviewservice.controller;

import com.sparta.reviewservice.dto.RequestDto;
import com.sparta.reviewservice.service.Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class Controller {

    private final Service service;

    //requestbody로 json데이터 dto로 변환할때 spring이 자동으로 setter적용시켜서 dto에 값을 넣어줌
    @PostMapping("/{productId}/reviews")
    public void createReview(@PathVariable Long productId
            , @Valid @RequestPart(value = "requestDto") RequestDto requestDto
            , @RequestPart(value="file") MultipartFile file) {
        service.createReviews(requestDto, productId, file);
    }




}
