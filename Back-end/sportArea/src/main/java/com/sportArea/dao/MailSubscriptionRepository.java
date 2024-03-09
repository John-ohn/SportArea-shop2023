package com.sportArea.dao;

import com.sportArea.entity.MailSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface MailSubscriptionRepository extends JpaRepository<MailSubscription, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT m FROM MailSubscription m WHERE m.email=:email")
    Optional<MailSubscription> findByEmail(@Param("email") String email);


    boolean existsByEmail(String email);
}
