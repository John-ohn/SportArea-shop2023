package com.sportArea.dao;

import com.sportArea.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress,Long> {

//    @Query("SELECT d FROM DeliveryAddress d WHERE d.user.userId =:userId")
//    Optional<DeliveryAddress > findByUserId(@Param("userId") Long userId);
}
