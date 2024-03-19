package com.sportArea.service.Imp;

import com.sportArea.dao.CommentRepository;
import com.sportArea.entity.Comment;
import com.sportArea.entity.Customer;
import com.sportArea.entity.Note;
import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentRequestDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.CommentService;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CommentServiceImp implements CommentService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImp.class);

    private final CustomerService customerService;

    private final ProductUAService productUAService;

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository, CustomerService customerService, ProductUAService productUAService) {
        this.commentRepository = commentRepository;
        this.customerService = customerService;
        this.productUAService = productUAService;
    }

    @Override
    public CommentRequestDTO findById(Long commentId) {
        Optional<Comment> responseOptional = commentRepository.findById(commentId);
        if (responseOptional.isPresent()) {

            CommentRequestDTO commentRequestDTO = createCommentDTOFromComment(responseOptional.get());
            logger.info("From CommentServiceImp method -findById- return CommentDTO by id: {} ", commentId);
            return commentRequestDTO;
        } else {
            logger.warn("From CommentServiceImp method -findById- send war message " +
                    "( Comment with commentId: {} is not available. ({}))", commentId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Comment with commentId: " + commentId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Comment findByIdWithoutDTO(Long commentId) {
        Optional<Comment> responseOptional = commentRepository.findById(commentId);
        if (responseOptional.isPresent()) {


            logger.info("From CommentServiceImp method -findById- return CommentDTO by id: {} ", commentId);
            return responseOptional.get();
        } else {
            logger.warn("From CommentServiceImp method -findById- send war message " +
                    "( Comment with commentId: {} is not available. ({}))", commentId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Comment with commentId: " + commentId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void save(Comment comment) {

        if (comment != null) {

            logger.info("From CommentServiceImp method -save- Save Comment by id: {} ", comment.getCommentId());
            commentRepository.save(comment);
        } else {
            logger.warn("From CommentServiceImp method -save- send war message " +
                    "( Comment  is not available. ({}))", HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Comment is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentRequestDTO> findAllUserComments(Long userId) {
        List<Comment> commentList = commentRepository.findAllUserComments(userId);
        if (!(commentList.size() == 0)) {

            List<CommentRequestDTO> commentRequestDTO = convertToCommentDTOList(commentList);

            logger.info("From CommentServiceImp method -findByCommentId- return List to CommentDTO by id: {} ", userId);
            return commentRequestDTO;
        } else {
            logger.warn("From CommentServiceImp method -findAllUserComments- send war message " +
                    "( Comment with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Comment> findAllUserCommentsWithoutDTO(Long userId) {
        List<Comment> commentList = commentRepository.findAllUserComments(userId);
        if (!(commentList.size() == 0)) {
            logger.info("From CommentServiceImp method -findByCommentId- return List to CommentDTO by id: {} ", userId);
            return commentList;
        } else {
            logger.warn("From CommentServiceImp method -findAllUserComments- send war message " +
                    "( Comment with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentRequestDTO> findAllProductComments(Long productId) {
        List<Comment> commentList = commentRepository.findAllProductComments(productId);
        if (!(commentList.size() == 0)) {
            List<CommentRequestDTO> commentRequestDTO = convertToCommentDTOList(commentList);

            logger.info("From CommentServiceImp method -findAllProductComments- return List to CommentDTO by id: {} ", productId);

            return commentRequestDTO;
        } else {
            logger.warn("From CommentServiceImp method -findAllProductComments- send war message " +
                    "( Comment with productId: {} is not available. ({}))", productId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Comment with productId: " + productId + " is not available. Product don't have comments.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<CommentRequestDTO> findAll() {
        List<Comment> commentList = commentRepository.findAll();
        if (!(commentList.size() == 0)) {
            List<CommentRequestDTO> commentRequestDTOList = convertToCommentDTOList(commentList);
            logger.info("From CommentServiceImp method -findAll- return List of Comments.");
            return commentRequestDTOList;
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
                    "(Comment with responseId: {} is not available. ({}) )", responseId, HttpStatus.BAD_REQUEST.name());
            throw new GeneralException("Comment with responseId: " + responseId + " is not available.", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<CommentRequestDTO> findCompanyComments() {
        List<Comment> commentList = commentRepository.findCompanyComments();

        if (!(commentList.size() == 0)) {
            List<CommentRequestDTO> commentRequestDTO = convertToCommentDTOList(commentList);

            logger.info("From CommentServiceImp method -findCompanyComments- return List to CommentDTO by Company Comments");

            return commentRequestDTO;
        } else {

            logger.warn("From CommentServiceImp method -findCompanyComments- send war message " +
                    "(Company Comments  List is empty. ({}))", HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Company Comments  List is empty.", HttpStatus.NOT_FOUND);

//            return new ArrayList<>();
        }
    }

    @Override
    public void deleteResponseIdsBetweenIds(Long startId, Long endId) {

    }

    @Override
    public void addProductComment(AddComment addComment) {

        if (!(addComment == null)) {
            Customer customer = customerService.findByIdInUser(addComment.getUserId());
            ProductUA productUA = productUAService.findByIdWithoutDTO(addComment.getProductId());

            Comment comment = Comment.builder()
                    .userName(customer.getFirstName())
                    .productRating( addComment.getProductRating())
                    .product(productUA)
                    .message(addComment.getMessage())
                    .dateTime(LocalDateTime.now())
                    .customer(customer)
                    .note(Note.FOR_PRODUCT)
                    .build();
            commentRepository.save(comment);

            addProductRating(addComment.getProductId());

            logger.info("Add new comment !");
        }
    }


//    public Comment createCommentFromCommentDTO(CommentRequestDTO commentRequestDTO) {
//        return Comment.builder()
//                .message(commentRequestDTO.getMessage())
//                .note(commentRequestDTO.getNote())
//                .customer(customerService.createUserFromUserDTO(commentRequestDTO.getUserRegistration()))
//                .product(productUAService.createProductFromProductUaDTO(commentRequestDTO.getProductDTO().getProductUa()))
//                .productRating(commentRequestDTO.getProductRating())
//                .build();
//    }

    public CommentRequestDTO createCommentDTOFromComment(Comment comment) {

        if (comment.getProduct() == null) {
            return CommentRequestDTO
                    .builder()
                    .commentId(comment.getCommentId())
                    .message(comment.getMessage())
                    .note(comment.getNote())
                    .userRegistration(customerService.createUserDTOFromUser(comment.getCustomer()))
                    .build();
        } else {
            return CommentRequestDTO
                    .builder()
                    .commentId(comment.getCommentId())
                    .message(comment.getMessage())
                    .note(comment.getNote())
                    .productRating(comment.getProductRating())
                    .userRegistration(customerService.createUserDTOFromUser(comment.getCustomer()))
                    .productDTO(productUAService.createProductDtoFromProductUA(comment.getProduct()))
                    .build();
        }
    }

    public List<CommentRequestDTO> convertToCommentDTOList(List<Comment> commentList) {

        List<CommentRequestDTO> commentRequestDTO = commentList.stream()
                .map(this::createCommentDTOFromComment)
                .toList();

        return commentRequestDTO;
    }

    public void addProductRating(Long productId) {
        Float productRating = commentRepository.getProductRating(productId);

        ProductUA productUA = productUAService.findByIdWithoutDTO(productId);
        productUA.setRating(productRating);

        productUAService.saveWithoutDTO(productUA);
        logger.info("Add new product rating: {}; productId: {} ", productRating, productId);
    }

}
