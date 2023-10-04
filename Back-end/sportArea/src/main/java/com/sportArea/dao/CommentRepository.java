package com.sportArea.dao;

import com.sportArea.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;


import javax.persistence.QueryHint;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.user u LEFT JOIN FETCH c.product p")
    List<Comment> findAllComment();

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.user  " +
            "LEFT JOIN FETCH c.product WHERE c.note = 'FOR_COMPANY'" )
    List<Comment> findCompanyComments();

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.user u " +
            "LEFT JOIN FETCH c.product " +
            "WHERE u.userId =:Id " )
    List<Comment> findAllUserComments(@Param("Id") Long userId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.user " +
            "LEFT JOIN FETCH c.product p " +
            "WHERE p.productId =:productId")
    List<Comment> findAllProductComments(@Param("productId") Long productId);

    @Modifying
    @Query(value = "INSERT INTO Comment (message, note, userId, productId, productRating) " +
            "VALUES (:message, :note, :userId, :productId, :productRating)", nativeQuery = true)
    void addProductComment(
            @Param("message") String message,
            @Param("note") String note,
            @Param("userId") Long userId,
            @Param("productId") Long productId,
            @Param("productRating") Float productRating
    );


    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"))
    @Query("SELECT AVG(c.productRating) From Comment c WHERE c.product.productId =:productId")
    Float getProductRating(@Param("productId") Long productId);
}
