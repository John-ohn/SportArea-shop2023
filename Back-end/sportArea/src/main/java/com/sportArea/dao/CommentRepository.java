package com.sportArea.dao;

import com.sportArea.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {

//    @Query("SELECT r FROM Response r LEFT JOIN FETCH r.userId u LEFT JOIN FETCH r.productId p")
//    List<Response> findAllR();

    @Query("SELECT r FROM Comment r LEFT JOIN FETCH r.userId  " +
            "LEFT JOIN FETCH r.productId WHERE r.note = 'FOR_COMPANY'" )
    List<Comment> findCompanyResponse();

    @Query("SELECT r FROM Comment r " +
            "LEFT JOIN FETCH r.userId u " +
            "LEFT JOIN FETCH r.productId " +
            "WHERE u.userId =:Id " )
    List<Comment> findAllUserComments(@Param("Id") Long userId);
}
