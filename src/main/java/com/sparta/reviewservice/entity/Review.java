package com.sparta.reviewservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //명세조건에서 product테이블은 추가 필드를 고려하지 않는다 했음으로
    //외래키를 가지고 있는 review 엔티티가 관계의 주인이다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false) //joincolumn : productId를 외래키로 설정
    private Product product;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private String content;

    // 선택적 이미지 URL 필드
    @Column(nullable = true)
    private String imageUrl;



}
