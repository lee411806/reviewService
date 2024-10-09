package com.sparta.reviewservice.controller;

import com.sparta.reviewservice.dto.RequestDto;
import com.sparta.reviewservice.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class Controller {

    private final Service service;

    @PostMapping("/{productId}/reviews")
    public void createReview(@PathVariable Long productId, @RequestBody RequestDto requestDto) {

    }


//    @GetMapping("/{productId}/reviews")
//    public List<ReviewResponseDto> getProductReviews(
//            @PathVariable Long productId,  // 경로 변수로 productId 받기
//            @RequestParam(required = false) Long cursor,  // 쿼리 파라미터로 cursor 받기
//            @RequestParam(defaultValue = "10") int size  // 쿼리 파라미터로 size 받기, 기본값 10
//    ) {
//        return service.getProductReviews(productId, cursor, size);
//    }


}
