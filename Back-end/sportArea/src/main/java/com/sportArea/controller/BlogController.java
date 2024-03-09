package com.sportArea.controller;

import com.sportArea.entity.dto.BlogDTO;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

    private final GeneralLogg generalLogg;
    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService, GeneralLogg generalLogg) {
        this.blogService = blogService;
        this.generalLogg = generalLogg;
    }

    @GetMapping
    public ResponseEntity<List<BlogDTO>> findAll() {
        List<BlogDTO> blogList = blogService.findAll();

        generalLogg.getLoggerControllerInfo("BlogController",
                "findAll",
                "/blogs",
                "List of Blog");

        return ResponseEntity.ok(blogList);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogDTO> findById(@PathVariable("blogId") Long blogId) {
        BlogDTO blog = blogService.findById(blogId);

        generalLogg.getLoggerControllerInfo("BlogController",
                "findById",
                "/blogs/{blogId}",
                "Blog with id" + blogId);

        return ResponseEntity.ok(blog);
    }

}
