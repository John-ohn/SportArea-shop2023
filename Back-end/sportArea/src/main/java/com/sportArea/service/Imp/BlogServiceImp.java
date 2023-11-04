package com.sportArea.service.Imp;

import com.sportArea.dao.BlogRepository;
import com.sportArea.entity.Blog;
import com.sportArea.entity.dto.BlogDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.BlogService;
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
public class BlogServiceImp implements BlogService {

    Logger logger = LoggerFactory.getLogger(BlogServiceImp.class);

    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImp(BlogRepository blogRepository){
        this.blogRepository=blogRepository;
    }

    @Override
    public List<BlogDTO> findAll(){
        List<Blog> blogs = blogRepository.findAll();

        if(!blogs.isEmpty()) {
            List<BlogDTO> blogList = convertToProductDTOList(blogs);
            logger.info("From BlogServiceImp method -findAll- return List of Blogs.");
            return blogList;
        }else{
            logger.warn("From BlogServiceImp method -findAll- send war message " +
                    "( Blog with blogId is not available. ({}))", HttpStatus.NOT_FOUND);
            throw new GeneralException("Don't find any Blog. Blogs list is empty.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public BlogDTO findById(Long blogId){

        Optional<Blog> blog = blogRepository.findById(blogId);
        if(blog.isPresent()){
            logger.info("From BlogServiceImp method -findById- return Blog by id: {} ", blogId);
            return createBlogDTOFromBlog (blog.get());
        }else {
            logger.warn("From BlogServiceImp method -findById- send war message " +
                    "( Blog with blogId {} is not available. ({}))", blogId, HttpStatus.NOT_FOUND);
            throw new GeneralException("Blog with blogId: " + blogId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    public BlogDTO createBlogDTOFromBlog(Blog blog) {
        return BlogDTO.builder()
                .blogId(blog.getBlogId())
                .title(blog.getTitle())
                .text(blog.getText())
                .urlMainImage(blog.getUrlMainImage())
                .build();
    }

    public List<BlogDTO> convertToProductDTOList(List<Blog> productList) {
        return productList
                .stream()
                .map(this::createBlogDTOFromBlog)
                .toList();
    }

}
