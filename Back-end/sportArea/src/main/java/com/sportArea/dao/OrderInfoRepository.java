package com.sportArea.dao;

import com.sportArea.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {


    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT o FROM OrderInfo o left join fetch o.customer u left join o.delivery WHERE u.userId =:userId ")
    Optional<OrderInfo> findByUserId(@Param("userId") Long userId);
}
