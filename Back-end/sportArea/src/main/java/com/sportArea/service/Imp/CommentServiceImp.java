package com.sportArea.service.Imp;

import com.sportArea.dao.CommentRepository;
import com.sportArea.entity.Comment;
import com.sportArea.entity.Note;
import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.CommentService;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CommentServiceImp implements CommentService {

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    private final UserService userService;

    private final ProductUAService productUAService;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository, UserService userService, ProductUAService productUAService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.productUAService = productUAService;
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
            throw new GeneralException("Comment with commentId: " + commentId + " is not available.", HttpStatus.NOT_FOUND);
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
            logger.warn("From CommentServiceImp method -findAllUserComments- send war message " +
                    "( Comment with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentDTO> findAllProductComments(Long productId) {
        List<Comment> commentList = commentRepository.findAllProductComments(productId);
        if (!(commentList.size() == 0)) {
            List<CommentDTO> commentDTO = convertToCommentDTOList(commentList);

            logger.info("From CommentServiceImp method -findAllProductComments- return List to CommentDTO by id: {} ", productId);

            return commentDTO;
        } else {
            logger.warn("From CommentServiceImp method -findAllProductComments- send war message " +
                    "( Comment with productId: {} is not available. ({}))", productId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Comment with productId: " + productId + " is not available. Product don't have comments.", HttpStatus.NOT_FOUND);
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
            throw new GeneralException("Comments is not available. The list of comments is empty.", HttpStatus.NOT_FOUND);
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
            throw new GeneralException("Comment with responseId: " + responseId + " is not available.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<CommentDTO> findCompanyComments() {
        List<Comment> commentList = commentRepository.findCompanyComments();

        return convertToCommentDTOList(commentList);
    }

    @Override
    public void deleteResponseIdsBetweenIds(Long startId, Long endId) {

    }

    @Override
    public void addProductComment(AddComment comment) {
        if (!(comment == null)) {
            comment.setNote(Note.FOR_PRODUCT);
            commentRepository.addProductComment(
                    comment.getMessage(),
                    comment.getNote().toString(),
                    comment.getUserId(),
                    comment.getProductId(),
                    comment.getProductRating()
            );

            addProductRating(comment.getProductId());

            logger.info("Add new comment !");
        }
    }


    public Comment createCommentFromCommentDTO(CommentDTO commentDTO) {
        return Comment.builder()
                .message(commentDTO.getMessage())
                .note(commentDTO.getNote())
                .user(userService.createUserFromUserDTO(commentDTO.getUserDTO()))
                .product(productUAService.createProductFromProductUaDTO(commentDTO.getProductDTO()))
                .productRating(commentDTO.getProductRating())
                .build();
    }

    public CommentDTO createCommentDTOFromComment(Comment comment) {

        if (comment.getProduct() == null) {
            return CommentDTO
                    .builder()
                    .commentId(comment.getCommentId())
                    .message(comment.getMessage())
                    .note(comment.getNote())
                    .userDTO(userService.createUserDTOFromUser(comment.getUser()))
                    .build();
        } else {
            return CommentDTO
                    .builder()
                    .commentId(comment.getCommentId())
                    .message(comment.getMessage())
                    .note(comment.getNote())
                    .productRating(comment.getProductRating())
                    .userDTO(userService.createUserDTOFromUser(comment.getUser()))
                    .productDTO(productUAService.createProductDTOFromProductUA(comment.getProduct()))
                    .build();
        }
    }

    public List<CommentDTO> convertToCommentDTOList(List<Comment> commentList) {

        List<CommentDTO> commentDTO = commentList.stream()
                .map(this::createCommentDTOFromComment)
                .toList();

        return commentDTO;
    }

    public void addProductRating(Long productId) {
        Float productRating = commentRepository.getProductRating(productId);

        ProductUaDTO productUA = productUAService.findById(productId);
        productUA.setRating(productRating);

        productUAService.save(productUA);
        logger.info("Add new product rating: {}; productId: {} ", productRating, productId);
    }

}
