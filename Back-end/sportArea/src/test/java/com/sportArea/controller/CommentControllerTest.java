package com.sportArea.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportArea.entity.*;
import com.sportArea.entity.dto.*;
import com.sportArea.entity.dto.userFeilds.ProductEnDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.CommentService;
import com.sportArea.service.ProductUAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
//@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @MockBean
    private ProductUAService productUAService;

    private UserRegistration userRegistration;

    private ProductUaDTO productUaDTO;

    private ProductDto productDTO;
    private CommentDTO commentDTO;

    private CommentDTO commentDTOTwo;

    @BeforeEach
    void createObject() {

        userRegistration = UserRegistration.builder()
                .userId(1L)
                .password("userPassword")
                .email("user@gmail.com")
                .role(Role.ROLE_USER)
                .phoneNumber("380911111111")
                .firstName("UserName")
                .lastName("UserLast")
                .status(Status.ACTIVE)
                .typeRegistration(TypeRegistration.FORM_REGISTRATION)
                .build();

        productUaDTO = ProductUaDTO.builder()
                .productId(1L)
                .productName("Solgar Vitamin E")
                .brands("Solgar")
                .type("Харчові добавки, Вітаміни")
                .subtype("Вітамін Е")
                .formOfIssue("Капсул")
                .producingCountry("Україна")
                .taste("Без смаку")
                .price(BigDecimal.valueOf(500))
                .currency("UA")
                .weight("200г")
                .article(231L)
                .productAmount(50)
                .description("Contraindications: individual intolerance of product components." +
                        "Not intended for use during pregnancy, lactation and persons under 18 years of age." +
                        "Before taking the drug, consult a doctor."
                )
                .productConsist("Склад на порцію (1 таблетка):\n" +
                        "Mg Цитрат 400 мг\n" +
                        "Mg 56 мг 15%\n" +
                        "Вітамін С 12 мг 15%\n"
                )
                .rating(5F)
                .status("В наявності")
                .promotion(false)
                .numberOfOrders(5L)
                .dateCreation(LocalDateTime.of(2023, 8, 5, 14, 47, 58))
                .urlImage("https://allnutrition.ua/produkt_img/f8b0i3189_d1200x1200.png")
                .build();

        productDTO = ProductDto.builder()
                .productId(productUaDTO.getProductId())
                .productUa(productUaDTO)
                .productEn(new ProductEnDTO())
                .build();

        commentDTO = CommentDTO.builder()
                .commentId(1L)
                .productRating(3.2F)
                .message("Massage")
                .note(Note.FOR_PRODUCT)
                .userRegistration(userRegistration)
                .productDTO(productDTO)
                .build();

        commentDTOTwo = CommentDTO.builder()
                .commentId(2L)
                .productRating(3.2F)
                .message("Massage")
                .note(Note.FOR_PRODUCT)
                .userRegistration(userRegistration)
                .productDTO(productDTO)
                .build();
    }

    @Test
    @DisplayName("Test CommentController method list")
    void testMethodList() throws Exception {

        List<CommentDTO> commentDTOList = List.of(commentDTO, commentDTOTwo);

        when(commentService.findAll()).thenReturn(commentDTOList);

        mockMvc.perform(get("/api/v1/comments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].commentId", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[0].productRating").value(3.2F))
                .andExpect(jsonPath("$[0].message").value("Massage"))
                .andExpect(jsonPath("$[0].userRegistration.email").value("user@gmail.com"))
                .andExpect(jsonPath("$[0].userRegistration.email", is("user@gmail.com")))
//                .andExpect(jsonPath("$[0]", is(commentDTO)))
        ;
    }

    @Test
    @DisplayName("Test CommentController method findCompanyComments")
    void testMethodsFindCompanyComments() throws Exception {

        CommentDTO commentFromCompany = CommentDTO.builder()
                .commentId(1L)
                .productRating(3.2F)
                .message("Massage")
                .note(Note.FOR_COMPANY)
                .userRegistration(userRegistration)
                .productDTO(new ProductDto())
                .build();

        List<CommentDTO> commentDTOList = List.of(commentFromCompany);

        when(commentService.findCompanyComments()).thenReturn(commentDTOList);

        mockMvc.perform(get("/api/v1/comments/company").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].commentId", containsInAnyOrder(1)))
                .andExpect(jsonPath("$[0].userRegistration.email", is("user@gmail.com")))
                .andExpect(jsonPath("$[0].note", is(Note.FOR_COMPANY.toString())));
    }

    @Test
    @DisplayName("Test CommentController method findAllUserComments")
    void testMethodFindAllUserComments() throws Exception {

        List<CommentDTO> list = List.of(commentDTO, commentDTOTwo);

        when(commentService.findAllUserComments(userRegistration.getUserId())).thenReturn(list);

        mockMvc.perform(get("/api/v1/comments/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].commentId", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[0].userRegistration.userId", is(1)))
                .andExpect(jsonPath("$[1].userRegistration.email", is("user@gmail.com")));

        when(commentService.findAllUserComments(2L)).thenThrow(new GeneralException(
                "User with userId: " + 2 + " is not available.", HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v1/comments/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    @Test
    @DisplayName("Test CommentController method findAllProductComments")
    void testMethodFindAllProductComments() throws Exception {
        List<CommentDTO> productCommentsList = List.of(commentDTO, commentDTOTwo);

        when(commentService.findAllProductComments(1L)).thenReturn(productCommentsList);

        mockMvc.perform(get("/api/v1/comments/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].commentId", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[0].productDTO.productId", is(1)))
                .andExpect(jsonPath("$[0].note", is(Note.FOR_PRODUCT.toString())))
                .andExpect(jsonPath("$[1].note", is(Note.FOR_PRODUCT.toString())));
    }

    @Test
    @DisplayName("Test CommentController method addProductComment")
    void testMethodAddProductComment() throws Exception, JsonProcessingException {
        AddComment comment = AddComment.builder()
                .productId(1L)
                .productRating(3.7F)
                .message("New product comment")
                .note(Note.FOR_PRODUCT)
                .userId(userRegistration.getUserId())
                .productId(productUaDTO.getProductId())
                .build();

        String requestBody = objectMapper.writeValueAsString(comment);

        Mockito.doNothing().when(commentService).addProductComment(Mockito.any());

        mockMvc.perform(post("/api/v1/comments/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Your comment was added successfully.")))
                .andExpect(jsonPath("$.message").value("Your comment was added successfully."));


    }

    @Test
    @DisplayName("Test CommentController method checkRating")
    void testMethodCheckRating() throws Exception {

        Mockito.doNothing().when(commentService).addProductRating(Mockito.any());

        when(productUAService.findById(productUaDTO.getProductId())).thenReturn(productUaDTO);

        mockMvc.perform(get("/api/v1/comments/checkRating/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Product rating is : " + productUaDTO.getRating()
                        + " product :" + productUaDTO.getProductName() + " " + productUaDTO.getProductId()));
    }
}