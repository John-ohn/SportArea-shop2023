package com.sportArea.dao;

import com.sportArea.entity.Blog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class BlogRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BlogRepository blogRepository;

    private Blog blogOne;

    @BeforeEach
    void createBlog(){
        blogOne= Blog.builder()
                .title("How be stronger")
                .text("Random text for test")
                .urlMainImage("https://www.dung.com/wp-content/uploads/2022/11/main-1.2.0.png")
                .build();
    }

    @Test
    @DisplayName("Test BlogRepositoryTest method findAll")
    void testMethodFindAll (){
        List<Blog> listEmptyStart = blogRepository.findAll();

        assertTrue(listEmptyStart.isEmpty());

        Blog blogSecond = Blog.builder()
                .title("How be goode")
                .text("Random text for test next step")
                .urlMainImage("https://www.dung.com/wp-content/uploads/2022/11/main-1.2.10.png")
                .build();


        testEntityManager.persist(blogOne);
        testEntityManager.persist(blogSecond);

        List<Blog> blogList = blogRepository.findAll();

        assertAll(
                ()-> assertNotNull(blogList),
                ()-> assertFalse(blogList.isEmpty()),
                ()-> assertEquals(2 ,blogList.size()),
                ()-> assertTrue(blogList.contains(blogOne)),
                ()-> assertTrue(blogList.contains(blogSecond)),
                ()-> assertEquals(blogOne, blogList.get(blogList.indexOf(blogOne)))
        );
    }

    @Test
    @DisplayName("Test BlogRepositoryTest method findById")
    void testMethodFindById (){
        testEntityManager.persist(blogOne);

        Optional<Blog> blog = blogRepository.findById(blogOne.getBlogId());

        assertAll(
                ()-> assertFalse(blog.isEmpty()),
                ()-> assertTrue(blog.isPresent()),
                ()-> assertEquals(blogOne, blog.orElse(new Blog()))
        );

        Optional<Blog> blogEmpty = blogRepository.findById(blogOne.getBlogId()+2);

        assertTrue(blogEmpty.isEmpty());
    }

}