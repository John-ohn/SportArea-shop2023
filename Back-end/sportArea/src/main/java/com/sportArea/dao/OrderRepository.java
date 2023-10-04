package com.sportArea.dao;

import com.sportArea.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("SELECT COUNT(o) FROM Order o WHERE o.product.productId = :productId")
    Long countOrdersByProduct(@Param("productId") Long productId);

}
