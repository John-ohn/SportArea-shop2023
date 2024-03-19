package com.sportArea.service;

import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentRequestDTO;

import java.util.List;


public interface CommentService {

    CommentRequestDTO findById(Long responseId);

    List<CommentRequestDTO> findAll();

    List<CommentRequestDTO> findCompanyComments();

    List<CommentRequestDTO> findAllUserComments(Long userId);

    List<CommentRequestDTO> findAllProductComments(Long productId);

    void delete(Long responseId);

    void deleteResponseIdsBetweenIds(Long startId, Long endId);

    void addProductComment(AddComment comment);

    void addProductRating(Long productId);

    List<Comment> findAllUserCommentsWithoutDTO (Long userId);

    Comment findByIdWithoutDTO(Long commentId);

    void save(Comment comment);

}
