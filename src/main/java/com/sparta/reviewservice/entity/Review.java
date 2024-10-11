package com.sparta.reviewservice.entity;

import com.sparta.reviewservice.dto.RequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
//UniqueConstraint : productId와 userId이 고유하다.
//단 유니크 조건 중복시 DataIntegrityViolationException 예외
@Table(name = "Review", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productId", "userId"})
})
//인덱스테이블 추가
//@Table(name = "Review",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"productId", "userId"})
//        },
//        indexes = {
//                @Index(name = "idx_product_id_id", columnList = "productId, id")
//        })
public class Review extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //명세조건에서 product테이블은 추가 필드를 고려하지 않는다 했음으로
    //외래키를 가지고 있는 review 엔티티가 관계의 주인이다.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", nullable = false) //joincolumn : productId를 외래키로 설정
    private Product product;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private String content;

    // 선택적 이미지 URL 필드
    @Column(nullable = true)
    private String imageUrl;

    @Version  // 낙관적 락을 위한 버전 필드
    private int version;

    public Review(RequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.score = requestDto.getScore();
        this.content = requestDto.getContent();
    }
}
