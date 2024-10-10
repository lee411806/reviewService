package com.sparta.reviewservice.repository;

import com.sparta.reviewservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 특정 Product에 속한 Review의 개수 세기
    long countById(Long id);
}
