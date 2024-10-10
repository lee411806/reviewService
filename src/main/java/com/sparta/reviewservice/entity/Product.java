package com.sparta.reviewservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//혹시나 다른 생성자 만들어줄때 기본생성자가 있어야지 엔티티가 생성되니까 만들어줌
//트랜잭션 시작 엔티티객체 영속성 컨텍스트에 옮기고 사용 이때 객체생성할때 필수적으로 빈 생성자 필요함
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동 생성
    private Long id; // DB의 bigint로 매핑 됨

    @Column(nullable = false)
    private Long reviewCount;  // 리뷰 개수를 BIGINT에 맞춰 Long으로 매핑

    @Column(nullable = false)
    private float score;  // FLOAT 타입에 맞춰 float 사용

}
