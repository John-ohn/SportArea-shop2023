package com.sportArea.dao;

import com.sportArea.entity.ProductUA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;

@Transactional
public interface ProductUARepository extends JpaRepository<ProductUA, Long> {

    @Modifying
    @Query("DELETE FROM ProductUA WHERE productId BETWEEN :startId AND :endId")
    void deleteBetweenIds(@Param("startId") Long startId, @Param("endId") Long endId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT prod FROM ProductUA prod WHERE prod.description LIKE %:keyWord%")
    List<ProductUA> searchByKeyWordInDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductUA p WHERE p.description LIKE %:keyWord%  ORDER BY p.price ASC")
    List<ProductUA> sortByPriceAscKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductUA p WHERE p.description LIKE %:keyWord% ORDER BY p.price DESC")
    List<ProductUA> sortByPriceDescKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductUA p WHERE p.description LIKE %:keyWord% ORDER BY p.rating DESC")
    List<ProductUA> sortByRatingDescKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT prod FROM ProductUA prod WHERE prod.type LIKE %:keyWord% OR prod.subtype LIKE %:keyWord% ")
    List<ProductUA> searchByKeyWordInTypeSubtype(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT prod FROM ProductUA prod WHERE prod.promotion = 1 ORDER BY prod.price ASC")
    List<ProductUA> searchByPromotionPrice();

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductUA p ORDER BY p.price ASC")
    List<ProductUA> sortByPriceAsc();

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductUA p ORDER BY p.price DESC")
    List<ProductUA> sortByPriceDesc();


}
