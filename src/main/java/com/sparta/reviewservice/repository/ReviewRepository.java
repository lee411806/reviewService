package com.sparta.reviewservice.repository;

import com.sparta.reviewservice.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    int countByProductId(Long productId);

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.product.id = :productId")
    float calculateAverageScore(@Param("productId") Long productId);

    @Query(value = "SELECT * FROM review WHERE product_id = :productId ORDER BY id DESC LIMIT :size", nativeQuery = true)
    List<Review> findReviewsByProductIdWithLimit(Long productId, int size);

    List<Review> findByProductIdAndIdLessThanEqualOrderByIdDesc(Long productId, Long cursor, Pageable pageable);





    /*
    * SELECT *
        FROM review
        WHERE product_id = {productId}
        ORDER BY created_at DESC
        LIMIT {size};
*/


    /*
    * SELECT *
        FROM review
        WHERE product_id = {productId}
        AND id < {cursor}
        ORDER BY created_at DESC
        LIMIT {size};
*/

}
