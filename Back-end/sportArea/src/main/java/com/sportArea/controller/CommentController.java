package com.sportArea.controller;

import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentRequestDTO;
import com.sportArea.entity.dto.GeneralResponse;
import com.sportArea.entity.dto.ProductDto;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.CommentService;
import com.sportArea.service.ProductUAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final GeneralLogg generalLogg;

    private final CommentService commentService;

    private final ProductUAService productUAService;

    @Autowired
    public CommentController(CommentService commentService, ProductUAService productUAService, GeneralLogg generalLogg) {
        this.commentService = commentService;
        this.productUAService = productUAService;
        this.generalLogg = generalLogg;
    }

    @GetMapping
    public ResponseEntity<List<CommentRequestDTO>> list() {
        List<CommentRequestDTO> commentList = commentService.findAll();
        generalLogg.getLoggerControllerInfo("CommentController",
                "list",
                "/api/v1/comments",
                "List of Comments.");
        return ResponseEntity.ok(commentList);
    }

    @GetMapping("/company")
    public ResponseEntity<List<CommentRequestDTO>> findCompanyComments() {
        List<CommentRequestDTO> commentList = commentService.findCompanyComments();
        generalLogg.getLoggerControllerInfo("CommentController",
                "findCompanyComments",
                "/company",
                "List of Company Comments.");
        return ResponseEntity.ok(commentList);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CommentRequestDTO>> findAllUserComments(@PathVariable("userId") Long userId) {

        List<CommentRequestDTO> commentList = commentService.findAllUserComments(userId);
        generalLogg.getLoggerControllerInfo("CommentController",
                "findAllUserComments",
                "/users/{userId}",
                "List of User Comments with userId: " + userId);

        return ResponseEntity.ok(commentList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<List<CommentRequestDTO>> findAllProductComments(@PathVariable("productId") Long productId) {
        List<CommentRequestDTO> commentList = commentService.findAllProductComments(productId);

        generalLogg.getLoggerControllerInfo("CommentController",
                "findAllProductComments",
                "/products/{productId}",
                "all Product Comments with productId: " + productId);

        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/product")
    public ResponseEntity<GeneralResponse> addProductComment(@RequestBody AddComment commentDTO) {

        commentService.addProductComment(commentDTO);

        generalLogg.getLoggerControllerInfo("CommentController",
                "addProductComment",
                "/product",
                "message(Your comment was added successfully.) and add new Product Comment to getProductId: "
                        + commentDTO.getProductId());

        GeneralResponse generalResponse = new GeneralResponse(
                HttpStatus.OK.value(),
                "Your comment was added successfully.");

        return ResponseEntity.ok(generalResponse);
    }

    @GetMapping("/checkRating/{productId}")
    public ResponseEntity<GeneralResponse> checkRating(@PathVariable("productId") Long productId) {
        commentService.addProductRating(productId);
        ProductDto product = productUAService.findByIdConvertToProductDto(productId);

        generalLogg.getLoggerControllerInfo("CommentController",
                "checkRating",
                "/checkRating/{productId}",
                "message( Product rating is : " + product.getProductUa().getRating()
                        + " product :" + product.getProductUa().getProductName() + " " + product.getProductId() + " )");

        GeneralResponse generalResponse = new GeneralResponse(
                HttpStatus.OK.value(),
                "Product rating is : " + product.getProductUa().getRating()
                        + " product :" + product.getProductUa().getProductName() + " " + product.getProductId());

        return ResponseEntity.ok(generalResponse);
    }

}
