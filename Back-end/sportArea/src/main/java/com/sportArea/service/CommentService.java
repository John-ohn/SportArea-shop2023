package com.sportArea.service;

import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.CommentDTO;

import java.util.List;


public interface CommentService {

    CommentDTO findById(Long responseId);

    List<CommentDTO> findAll();

    List<Comment> findCompanyComments();

    List<CommentDTO> findAllUserComments(Long userId);

    void delete(Long responseId);

    void deleteResponseIdsBetweenIds(Long startId, Long endId);

    Comment save(Comment comment);

}
