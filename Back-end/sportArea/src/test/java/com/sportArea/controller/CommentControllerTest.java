package com.sportArea.controller;

import com.sportArea.config.SecurityConfig;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.security.JwtConfigurer;
import com.sportArea.security.JwtTokenProvider;
import com.sportArea.service.CommentService;
import com.sportArea.service.ProductUAService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;




@SpringBootTest
//@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private  GeneralLogg generalLogg;

    @MockBean
    private  ProductUAService productUAService;

//    @MockBean
//    private JwtConfigurer jwtConfigurer;

//    @MockBean
//    private JwtTokenProvider jwtTokenProvider;

//    @InjectMocks
//    private CommentController CommentController;


    @Test
    @DisplayName("Test CommentController method list")
    void testMethodList() throws Exception {

        List<CommentDTO> commentDTOList = List.of(new CommentDTO(), new CommentDTO());

        when(commentService.findAll()).thenReturn(commentDTOList);

        mockMvc.perform(get("/api/v1/comments"))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

    }

    @Test
    void findCompanyComments() {
    }

    @Test
    void findAllUserComments() {
    }

    @Test
    void findAllProductComments() {
    }

    @Test
    void addProductComment() {
    }

    @Test
    void checkRating() {
    }
}