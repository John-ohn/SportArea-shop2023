package com.sportArea.controller;

import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.service.CommentService;
import com.sportArea.service.ProductUAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    private final ProductUAService productUAService;



    @Autowired
    public CommentController(CommentService commentService, ProductUAService productUAService) {
        this.commentService = commentService;
        this.productUAService = productUAService;
    }

    @GetMapping
    public List<CommentDTO> list() {
        return commentService.findAll();
    }

    @GetMapping("/company")
    public List<CommentDTO> findCompanyComments() {
        return commentService.findCompanyComments();
    }

    @GetMapping("/users/{userId}")
    public List<CommentDTO> findAllUserComments(@PathVariable("userId") Long userId) {
        return commentService.findAllUserComments(userId);
    }

    @GetMapping("/products/{productId}")
    public List<CommentDTO> findAllProductComments(@PathVariable("productId") Long productId) {
        List<CommentDTO> commentDTOList = commentService.findAllProductComments(productId);
        logger.info("From controller -product-comments-. Send all Product Comments with productId: {}", productId);

        return commentDTOList;
    }

    @PostMapping("/product")
    public ResponseEntity<String> addProductComment(@RequestBody AddComment commentDTO) {

        commentService.addProductComment(commentDTO);
        logger.info("From controller -addProductComment-. Add  Product Comment with comment : {} ", commentDTO);
        return ResponseEntity.ok("Comment are added.");
    }

    @GetMapping("/checkRating/{productId}")
    public ResponseEntity<String> checkRating(@PathVariable("productId") Long productId) {
        commentService.addProductRating(productId);
        ProductUaDTO productUA = productUAService.findById(productId);
        return ResponseEntity.ok(
                "product rating is : " + productUA.getRating()
                + " product :" + productUA.getProductName()+" " + productUA.getProductId());
    }

}
