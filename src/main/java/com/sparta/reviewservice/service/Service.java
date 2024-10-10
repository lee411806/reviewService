package com.sparta.reviewservice.service;

import com.sparta.reviewservice.dto.RequestDto;
import com.sparta.reviewservice.entity.Product;
import com.sparta.reviewservice.entity.Review;
import com.sparta.reviewservice.repository.ProductRepository;
import com.sparta.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    //final붙여준 이유는 처음 초기화하고 변경하지 않기 위해 (일관성 유지)
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createReviews(RequestDto requestDto, Long productId, MultipartFile file) {

        //Product 만들어줌
        // Product가 DB에 존재하는지 확인
        Product product = productRepository.findById(productId)
                .orElseGet(() -> {
                    Product newProduct = new Product();
                    newProduct.setId(productId);
                    newProduct.setReviewCount(0L);
                    newProduct.setScore(0.0F);
                    System.out.println("New product: " + newProduct);
                    System.out.println(newProduct.getReviewCount());
                    return productRepository.save(newProduct);
                });

// 기존 product의 reviewCount를 가져옴
        long currentReviewCount = product.getReviewCount();
        System.out.println("현재 리뷰 카운트: " + currentReviewCount);

// 리뷰 개수 증가
        product.setReviewCount(currentReviewCount + 1);
        System.out.println("리뷰 카운트 증가 후: " + product.getReviewCount());

// 평균 점수 계산 (기존 점수와 새로운 리뷰 점수를 반영)
        float newScore = (product.getScore() * currentReviewCount + requestDto.getScore()) / (currentReviewCount + 1);
        product.setScore(newScore);

        // Product 저장
        productRepository.save(product);

        //리뷰 만들어줌
        Review review = new Review(requestDto);
        review.setProduct(product);

        // 이미지 파일이 있을 경우 처리
        //!file.isEmpty() 전송 되었는데 파일크기가 0인 경우
        if (file != null && !file.isEmpty()) {
            // 실제로는 S3 서비스로 이미지를 업로드한 후 반환된 URL을 사용합니다.
            review.setImageUrl(file.getOriginalFilename());
        } else {
            review.setImageUrl(null); // 이미지가 없으면 null로 설정
        }



        reviewRepository.save(review);

    }
}
