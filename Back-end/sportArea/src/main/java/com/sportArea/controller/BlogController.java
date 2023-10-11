package com.sportArea.controller;

import com.sportArea.entity.Blog;
import com.sportArea.entity.dto.BlogDTO;
import com.sportArea.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/blog")
public class BlogController {

    Logger logger = LoggerFactory.getLogger(BlogController.class);
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/list")
    public List<BlogDTO> findAll() {
        List<BlogDTO> blogList = blogService.findAll();

        logger.info("From BlogController method -findAll- /blog/list. Return List of Blog");

        return blogList;
    }

    @GetMapping("/{blogId}")
    public BlogDTO findById(@PathVariable("blogId") Long blogId) {
        BlogDTO blog = blogService.findById(blogId);
        logger.info("From BlogController method -findById- /blog/{blogId}. Return Blog");
        return blog;
    }

}
