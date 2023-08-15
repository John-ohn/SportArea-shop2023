package com.sportArea.dao;

import com.sportArea.entity.ProductUA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ProductUARepository extends JpaRepository<ProductUA, Long> {

    @Modifying
    @Query("DELETE FROM ProductUA WHERE productId BETWEEN :startId AND :endId")
    void deleteBetweenIds(@Param("startId") Long startId, @Param("endId") Long endId);


    @Query("SELECT prod FROM ProductUA prod WHERE prod.description LIKE %:keyWord%")
    List<ProductUA> searchByKeyWord(@Param("keyWord") String keyWord);

}
