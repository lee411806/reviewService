package com.sparta.reviewservice.repository;

import com.sparta.reviewservice.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 비관적 락을 걸어서 Product 조회
    // Product data를 dummy 로 넣어주지 않았기때문에 만약 productId가 1,2가 없는 상태에서 3을 조회한다면
    // entity의 autoincrement로 인해 1,2가 만들어진 후 3이 생성되어 코드가 작동하기 때문에 write lock 걸어준다.
    //findbyId이지만 Lock은 repository에서만 걸어줄 수 있음으로 명시적으로 메서드 생성
    //++ review 가 동시에 여러개 들어왔을 때 product 리뷰카운트 동시성 문제 발생가능 -> 비관적 락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdForUpdate(Long productId);
}
