package com.sportArea.service.Imp;

import com.sportArea.dao.CommentRepository;
import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.exception.CommentException;
import com.sportArea.exception.UserException;
import com.sportArea.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImp implements CommentService {

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDTO findById(Long commentId) {
        Optional<Comment> responseOptional = commentRepository.findById(commentId);
        if (responseOptional.isPresent()) {

            CommentDTO commentDTO = createCommentDTOFromComment(responseOptional.get());
            logger.info("From CommentServiceImp method -findById- return CommentDTO by id: {} ", commentId);
            return commentDTO;
        } else {
            logger.warn("From CommentServiceImp method -findById- send war message " +
                    "( Comment with commentId: {} is not available. ({}))", commentId, HttpStatus.NOT_FOUND.name());
            throw new CommentException("Comment with commentId: " + commentId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentDTO> findAllUserComments(Long userId) {
        List<Comment> commentList = commentRepository.findAllUserComments(userId);
        if (!(commentList.size() == 0)) {

            List<CommentDTO> commentDTO = convertToCommentDTOList(commentList);

            logger.info("From CommentServiceImp method -findByCommentId- return List to CommentDTO by id: {} ", userId);
            return commentDTO;
        } else {
            logger.warn("From CommentServiceImp method -findById- send war message " +
                    "( Comment with responseId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND.name());
            throw new CommentException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentDTO> findAll() {
        List<Comment> commentList = commentRepository.findAll();
        if (!(commentList.size() == 0)) {
            List<CommentDTO> commentDTOList = convertToCommentDTOList(commentList);
            logger.info("From CommentServiceImp method -findAll- return List of Comments.");
            return commentDTOList;
        } else {
            throw new CommentException("Comments is not available. The list of comments is empty.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void delete(Long responseId) {
        Optional<Comment> responseOptional = commentRepository.findById(responseId);
        if (responseOptional.isPresent()) {
            commentRepository.delete(responseOptional.get());
            logger.info("From CommentServiceImp method -delete- return message (Comment with commentId: {} was deleted.).", responseId);
        } else {
            logger.warn("From CommentServiceImp method -delete- send war message " +
                    "(Comment with responseId: {} is not available. ({}) )", responseId, HttpStatus.NOT_FOUND.name());
            throw new CommentException("Comment with responseId: " + responseId + " is not available.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<Comment> findCompanyComments() {
        return commentRepository.findCompanyComments();
    }

    @Override
    public void deleteResponseIdsBetweenIds(Long startId, Long endId) {

    }

    @Override
    public Comment save(Comment comment) {
        return null;
    }

    public CommentDTO createCommentDTOFromComment(Comment comment) {

        return CommentDTO
                .builder()
                .commentId(comment.getCommentId())
                .message(comment.getMessage())
                .note(comment.getNote())
                .userId(comment.getUserId())
                .productId(comment.getProductId())
                .build();
    }

    public List<CommentDTO> convertToCommentDTOList(List<Comment> commentList) {
        return commentList
                .stream()
                .map(this::createCommentDTOFromComment)
                .toList();
    }

}
