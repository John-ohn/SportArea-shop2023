package com.sportArea.service.Imp;

import com.sportArea.dao.CommentRepository;
import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.CommentDTO;
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
    public CommentDTO findById(Long responseId) {
        Optional<Comment> responseOptional = commentRepository.findById(responseId);
        if (responseOptional.isPresent()) {
            Comment comment = responseOptional.get();
            CommentDTO commentDTO = createCommentDTOFromComment(comment);
            logger.info("From ResponseServiceImp method -findById- return ResponseDTO by id: {} ", responseId);
            return commentDTO;
        } else {
            logger.warn("From ResponseServiceImp method -findById- send war message " +
                    "( Response with responseId: {} is not available. ({}))", responseId, HttpStatus.NOT_FOUND.name());
            throw new UserException("User with userId: " + responseId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentDTO> findAllUserComments(Long userId) {
        List<Comment> commentOptional = commentRepository.findAllUserComments(userId);
        if (!(commentOptional.size() == 0)) {

            List<CommentDTO> commentDTO = commentOptional
                    .stream()
                    .map(this::createCommentDTOFromComment)
                    .collect(Collectors.toList());

            logger.info("From ResponseServiceImp method -findByResponseId- return List to ResponseDTO by id: {} ", userId);
            return commentDTO;
        } else {
            logger.warn("From ResponseServiceImp method -findById- send war message " +
                    "( Response with responseId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND.name());
            throw new UserException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> commentList = commentRepository.findAll();

        logger.info("From ResponseServiceImp method -findAll- return List of Response.");
        return commentList;
    }

    @Override
    public void delete(Long responseId) {
        Optional<Comment> responseOptional = commentRepository.findById(responseId);
        if (responseOptional.isPresent()) {
            commentRepository.delete(responseOptional.get());
            logger.info("From ResponseServiceImp method -delete- return message (Response with responseId: {} was deleted.).", responseId);
        } else {
            logger.warn("From ResponseServiceImp method -delete- send war message " +
                    "(Response with responseId: {} is not available. ({}) )", responseId, HttpStatus.NOT_FOUND.name());
            throw new UserException("Response with responseId: " + responseId + " is not available.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<Comment> findCompanyResponse() {
        return commentRepository.findCompanyResponse();
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
}
