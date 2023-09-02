package com.sportArea.controller;

import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    Logger logger= LoggerFactory.getLogger(CommentController.class);
   private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/list")
    public List<CommentDTO> list (){
       return commentService.findAll();
    }

    @GetMapping("/company-comments")
    public List<Comment> findCompanyComments (){
        return commentService.findCompanyComments();
    }

    @GetMapping("/user-comments/{userId}")
    public List<CommentDTO> findAllUserComments (@PathVariable("userId") Long userId){
        return commentService.findAllUserComments(userId);
    }

    @GetMapping("/product-comments/{productId}")
    public List<CommentDTO> findAllProductComments(@PathVariable("productId") Long productId){
        List<CommentDTO> commentDTOList = commentService.findAllProductComments(productId);
        logger.info("From controller -product-comments-. Send all Product Comments with productId: {}",productId);

        return commentDTOList;

    }





}
