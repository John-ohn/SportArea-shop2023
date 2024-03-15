package com.sportArea.dao;

import com.sportArea.entity.ProductEN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.math.BigDecimal;
import java.util.List;

public interface ProductENRepository extends JpaRepository<ProductEN, Long> {

    @Modifying
    @Query("DELETE FROM ProductEN WHERE productId BETWEEN :startId AND :endId")
    void deleteBetweenIds(@Param("startId") Long startId, @Param("endId") Long endId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT prod FROM ProductEN prod WHERE prod.description LIKE %:keyWord%")
    List<ProductEN> searchByKeyWordInDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord%  ORDER BY p.price ASC")
    List<ProductEN> sortByPriceAscKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord% ORDER BY p.price DESC")
    List<ProductEN> sortByPriceDescKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord% ORDER BY p.rating DESC")
    List<ProductEN> sortByRatingDescKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord% ORDER BY p.productName ASC")
    List<ProductEN> sortByProductNameAscKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord% ORDER BY p.numberOfOrders DESC")
    List<ProductEN> sortByNumberOfOrdersDescKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord% " +
            "AND p.price BETWEEN :lowPrice AND :highPrice " +
            "ORDER BY p.price ASC")
    List<ProductEN> sortByPriceBetweenKeyWordDescription(
            @Param("keyWord") String keyWord,
            @Param("lowPrice") BigDecimal lowPrice,
            @Param("highPrice")BigDecimal highPrice
    );

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT p FROM ProductEN p WHERE p.description LIKE %:keyWord% ORDER BY p.dateCreation DESC")
    List<ProductEN> sortByTimeKeyWordDescription(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT prod FROM ProductEN prod WHERE prod.type LIKE %:keyWord% OR prod.subtype LIKE %:keyWord% ")
    List<ProductEN> searchByKeyWordInTypeSubtype(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT prod FROM ProductEN prod WHERE prod.promotion = true ORDER BY prod.price ASC")
    List<ProductEN> searchByPromotionPrice();
}
