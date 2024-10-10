package com.sparta.reviewservice.service;

import com.sparta.reviewservice.dto.RequestDto;
import com.sparta.reviewservice.dto.ReviewDto;
import com.sparta.reviewservice.dto.ReviewResponseDto;
import com.sparta.reviewservice.entity.Product;
import com.sparta.reviewservice.entity.Review;
import com.sparta.reviewservice.repository.ProductRepository;
import com.sparta.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
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


        try {
            // 리뷰 저장
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException e) {
            // 중복된 리뷰가 있을 때 예외 처리 (entity에 productid랑 userid 유니크 처리해준 에러처리)
            // 중복 예외 발생 시 로그 확인
            System.out.println("예외 발생: 이미 리뷰가 존재합니다.");
            throw new IllegalStateException("이미 해당 상품에 대한 리뷰를 작성했습니다.", e);
        }

    }

    @Transactional
    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        // 1. 리뷰 총 개수 조회
        int totalCount = reviewRepository.countByProductId(productId);

        // 2. 평균 점수 계산
        float score = reviewRepository.calculateAverageScore(productId);

        // 3. 리뷰 목록을 페이징하여 조회 (커서 기반 페이징)
        Pageable pageable = PageRequest.of(0, size); // Pageable 객체 생성 ,커서기반으로 paging할때 pageable로 매개변수 받음
        List<Review> reviews;
        if (cursor == null) {
            // 첫 페이지 요청인 경우, 가장 최신 리뷰부터 size 만큼 가져옴
            reviews = reviewRepository.findReviewsByProductIdWithLimit(productId, size);
            System.out.println("cursor에 Null값이 들어오고 메서드가 잘 실행이 되었다: " + cursor);
        } else {
            // 커서 기반으로 페이징
            reviews = reviewRepository.findByProductIdAndIdLessThanEqualOrderByIdDesc(productId, cursor, pageable);
            System.out.println("cursor가 Null이 아니고 잘 실행이 되었다: " + cursor);
        }

        // 4. 다음 커서 설정 (리뷰가 남아있다면 마지막 리뷰의 ID를 커서로 사용)
        Long nextCursor = !reviews.isEmpty() ? reviews.get(reviews.size() - 1).getId() : null;

        // 5. ReviewDto 리스트로 변환
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getUserId(),
                        review.getScore(),
                        review.getContent(),
                        review.getImageUrl(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());

        // 6. 최종 응답 객체 반환
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setTotalCount(totalCount);
        responseDto.setScore(score);
        responseDto.setCursor(nextCursor);
        responseDto.setReviews(reviewDtos);

        return responseDto;
    }
}
