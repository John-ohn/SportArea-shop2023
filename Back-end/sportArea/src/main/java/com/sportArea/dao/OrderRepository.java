package com.sportArea.dao;

import com.sportArea.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderInfo oi LEFT JOIN FETCH o.products WHERE oi.user.userId =:userId")
    Optional<Order> findByUserId(@Param("userId") Long userId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderInfo oi LEFT JOIN FETCH o.products WHERE oi.user.userId =:userId")
    List<Order> findAllByUserId(@Param("userId") Long userId);

//    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
//    @Query("SELECT COUNT(o) FROM Order o WHERE o.product.productId = :productId")
//    Long countOrdersByProduct(@Param("productId") Long productId);

}
