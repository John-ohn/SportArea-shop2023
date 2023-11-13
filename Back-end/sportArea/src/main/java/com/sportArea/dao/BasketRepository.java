package com.sportArea.dao;

import com.sportArea.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {


    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT b FROM Basket b LEFT join b.productUA LEFT join b.customer WHERE b.customer.guestId=:guestId")
    List<Basket> findByGuestId(@Param("guestId") Long guestId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT b FROM Basket b LEFT join b.productUA LEFT join b.user WHERE b.user.userId=:userId")
    List<Basket> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM Basket b   WHERE b.customer.guestId=:guestId")
    void deleteByGuestId(@Param("guestId") Long guestId);


}
