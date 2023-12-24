package com.sportArea.dao;

import com.sportArea.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.QueryHint;
import java.util.Optional;


public interface UserRepository extends JpaRepository<Customer, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    Optional<Customer> findByEmail(String email);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT u FROM Customer u WHERE u.firstName LIKE %:keyWord% OR u.email LIKE %:keyWord% ")
    Customer findByEmailAndFirstName(@Param("keyWord") String keyWord);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT  u FROM Customer u WHERE u.userId=:UserId")
    Optional<Customer> findByUserId(@Param("UserId") Long userId);

    @Modifying
    @Query("DELETE  FROM Customer  WHERE userId BETWEEN :startId AND :endId")
    void deleteBetweenIds(@Param("startId") Long startId, @Param("endId") Long endId);

    @Modifying
    @Query("UPDATE Customer c SET c.firstName =:value WHERE c.userId =:userId")
//    @Transactional
    void updateUserFirstName(@Param("userId") Long userId, @Param("value") String value);

    @Modifying
    @Query("UPDATE Customer u SET u.lastName = :value WHERE u.userId = :userId")
    void updateUserLastName(@Param("userId") Long userI, @Param("value") String value);

    @Modifying
    @Query("UPDATE Customer u SET u.email = :value WHERE u.userId = :userId")
    void updateUserEmail(@Param("userId") Long userI, @Param("value") String value);

    @Modifying
    @Query("UPDATE Customer u SET u.phoneNumber = :value WHERE u.userId = :userId")
    void updateUserPhoneNumber(@Param("userId") Long userI, @Param("value") String value);

    @Modifying
    @Query("UPDATE Customer u SET u.password = :value WHERE u.userId = :userId")
    void updateUserPassword(@Param("userId") Long userI, @Param("value") String value);

    @Modifying
    @Query("UPDATE Customer u SET u.status = :value WHERE u.userId = :userId")
    void updateUserStatus(@Param("userId") Long userI, @Param("value") String value);
}
