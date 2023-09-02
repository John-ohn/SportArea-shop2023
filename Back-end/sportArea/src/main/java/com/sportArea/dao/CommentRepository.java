package com.sportArea.dao;

import com.sportArea.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;


import javax.persistence.QueryHint;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.userId u LEFT JOIN FETCH c.productId p")
    List<Comment> findAllComment();

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.userId  " +
            "LEFT JOIN FETCH c.productId WHERE c.note = 'FOR_COMPANY'" )
    List<Comment> findCompanyComments();

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.userId u " +
            "LEFT JOIN FETCH c.productId " +
            "WHERE u.userId =:Id " )
    List<Comment> findAllUserComments(@Param("Id") Long userId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.userId " +
            "LEFT JOIN FETCH c.productId p " +
            "WHERE p.productId =:productId")
    List<Comment> findAllProductComments(@Param("productId") Long productId);
}
