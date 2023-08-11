package com.sportArea.dao;

import com.sportArea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


    @Modifying
    @Query("DELETE  FROM User  WHERE userId BETWEEN :startId AND :endId")
    void deleteBetweenIds(@Param("startId") Long startId, @Param("endId") Long endId);
}
