package com.sportArea.service;

import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentService {

    CommentDTO findById(Long responseId);

    List<CommentDTO> findAll();

    List<CommentDTO> findCompanyComments();

    List<CommentDTO> findAllUserComments(Long userId);

    List<CommentDTO> findAllProductComments(Long productId);

    void delete(Long responseId);

    void deleteResponseIdsBetweenIds(Long startId, Long endId);

    void addProductComment(AddComment comment);

    void addProductRating(Long productId);

}
