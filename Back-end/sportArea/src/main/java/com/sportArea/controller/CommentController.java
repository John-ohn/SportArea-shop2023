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
    CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/list")
    public List<Comment> list (){
       return commentService.findAll();
    }

    @GetMapping("/company-response")
    public List<Comment> findCompanyResponse (){
        return commentService.findCompanyResponse();
    }

    @GetMapping("/{id}")
    public List<CommentDTO> findAllUserComments (@PathVariable("id") Long id){
        return commentService.findAllUserComments(id);
    }



}
