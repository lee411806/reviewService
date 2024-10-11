package com.sparta.reviewservice.service;

import com.sparta.reviewservice.dto.ReviewResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private Service service;

   /* @Test
    public void testReviewQueryPerformance() {
        Long productId = 1L; // 테스트할 productId
        Long cursor = 6L;  // 첫 페이지
        int size = 10;       // 페이징 크기

        // 성능 테스트 - 쿼리 실행 시간 측정
        long startTime = System.currentTimeMillis();
        ReviewResponseDto response = service.getReviews(productId, cursor, size);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + elapsedTime + "ms");
        //일반 179ms ,, 인덱스 테이블 145ms
    }*/
}


