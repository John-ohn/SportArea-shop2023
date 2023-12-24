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
            titek(blogList);
            logger.info("From BlogServiceImp method -findAll- return List of Blogs.");
//            logger.info(blogList.get(0).getText());
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

    public  void titek( List<BlogDTO> list){


//        BlogDTO one = list.get(0);
//        one.setSubTitle(cutText(one.getText()));
//        BlogDTO two = list.get(1);
//        two.setSubTitle(cutText2(two.getText()));
        for (int i =0; i<list.size(); i++){
            if(i==0){
                list.get(i).setSubTitle(BlogServiceImp.cutText(list.get(i).getText()));
            }else if(i==1){
                list.get(i).setSubTitle(cutText2(list.get(i).getText()));
            }
            else {
                list.get(i).setSubTitle(cutText2(list.get(i).getText()));
            }

        }


    }

    public static String cutText(String text){
        String [] stringList = text.split(" ");
        String titelHome="";
        for(int i = 0; i<24; i++ ){
                if(i==0){
                    titelHome=stringList[i];
                }else {
                    titelHome = titelHome + " " + stringList[i];
                    if (i == 23) {
                        titelHome = titelHome + "...";
                    }
                }
        }
        String result = titelHome.replace("<p>", "");

        return result;

    }

    public static String cutText2(String text) {
        String[] stringList = text.split(" ");
        String titelHome = "";
        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                titelHome = stringList[i];
            } else {
                titelHome = titelHome + " " + stringList[i];
                if (i == 24) {
                    titelHome = titelHome + "...<p>";
                }
            }
        }


        String result = titelHome.replace("<p>", "");
        return result;


    }

}
