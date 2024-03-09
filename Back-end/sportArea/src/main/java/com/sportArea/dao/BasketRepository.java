package com.sportArea.dao;

import com.sportArea.entity.Basket;
import com.sportArea.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {


    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT b FROM Basket b left join fetch b.customer left join fetch b.products WHERE b.customer.userId=:userId")
    Optional<Basket> findByUserId(@Param("userId") Long guestId);



    @Modifying
    @Query("UPDATE Basket b SET " +
            "b.basketTotalPrice=:totalPrice," +
            " b.productQuantity=:quantity WHERE b.customer.userId=:userId")
     void update(@Param("totalPrice")BigDecimal totalPrice,
                   @Param("quantity") Integer quantity,
                   @Param("userId") Long userId);

//    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
//    @Query("SELECT b FROM Basket b LEFT join b.productUA LEFT join b.user WHERE b.user.userId=:userId")
//    List<Basket> findByUserId(@Param("userId") Long userId);

//    @Modifying
//    @Query("DELETE FROM Basket b   WHERE b.customer.guestId=:guestId")
//    void deleteByGuestId(@Param("guestId") Long guestId);


}
