package com.sportArea.controller;

import com.sportArea.entity.Comment;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/response")
public class CommentController {

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

    @GetMapping("/{id}")
    public List<CommentDTO> findAllUserComments (@PathVariable("id") Long id){
        return commentService.findAllUserComments(id);
    }





}
