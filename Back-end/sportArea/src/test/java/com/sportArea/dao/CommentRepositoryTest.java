package com.sportArea.dao;

import com.sportArea.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    private Comment commentOne;
    private Comment commentSecond;
    private Comment commentCompany;
    private Customer customer;
    private ProductUA productUA;

    @BeforeEach
    void createComment() {
         productUA = ProductUA.builder()
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
                .description("Протипоказання: індивідуальна нестерпність компонентів продукту. " +
                        "Не призначений для використання при вагітності, лактації та особами до 18 років. " +
                        "Перед прийомом препарату проконсультуйтеся з лікарем."
                )
                .productConsist("Склад на порцію (1 таблетка):\n" +
                        "Mg Цитрат 400 мг\n" +
                        "Mg 56 мг 15%\n" +
                        "Вітамін С 12 мг 15%\n"
                )
                .rating(3.4F)
                .status("В наявності")
                .promotion(0)
                .numberOfOrders(5L)
                .dateCreation(LocalDateTime.of(2023, 8, 5, 14, 47, 58))
                .urlImage("https://allnutrition.ua/produkt_img/f8b0i3189_d1200x1200.png")
                .build();
         customer = Customer.builder()
                .userId(1L)
                .firstName("Ronald")
                .lastName("Serous")
                .email("sewewt@code.com")
                .phoneNumber("380342312275")
                .password("As123ertyuer")
                .role(Role.ROLE_USER)
                .status(Status.ACTIVE)
                .typeRegistration(TypeRegistration.FORM_REGISTRATION)
                .build();
        customer = entityManager.merge(customer);
        productUA= entityManager.merge(productUA);

        commentOne = Comment.builder()
                .userName("Ronald")
                .message("Good message")
                .note(Note.FOR_PRODUCT)
                .productRating(3.4F)
                .dateTime(LocalDateTime.of(2023, 8, 10, 10, 12))
                .customer(customer)
                .product(productUA)
                .build();

        commentSecond = Comment.builder()
                .userName("Ronald")
                .message("Good message from user")
                .note(Note.FOR_PRODUCT)
                .productRating(3.9F)
                .dateTime(LocalDateTime.of(2023, 9, 2, 20, 12))
                .customer(customer)
                .product(productUA)
                .build();

        commentCompany = Comment.builder()
                .userName("Ronald")
                .message("Good message from Company")
                .note(Note.FOR_COMPANY)
                .dateTime(LocalDateTime.of(2023, 9, 4, 14, 12))
                .customer(customer)
                .build();
    }

    @Test
    @DisplayName("Test CommentRepository method findAllComment")
    void findAllComment() {
        List<Comment> commentListEmpty = commentRepository.findAllComment();

        assertTrue(commentListEmpty.isEmpty());
        
        entityManager.persist(commentOne);
        entityManager.persist(commentSecond);
        entityManager.persist(commentCompany);

        List<Comment> commentList = commentRepository.findAllComment();

        assertAll(
                () -> assertNotNull(commentList),
                () -> assertFalse(commentList.isEmpty()),
                () -> assertEquals(3, commentList.size()),
                () -> assertTrue(commentList.contains(commentCompany)),
                () -> assertTrue(commentList.contains(commentSecond))
        );
    }

    @Test
    @DisplayName("Test CommentRepository method findCompanyComments")
    void findCompanyComments() {

        List<Comment> commentListEmpty = commentRepository.findCompanyComments();

        assertTrue(commentListEmpty.isEmpty());

        entityManager.persist(commentCompany);

        List<Comment> commentList = commentRepository.findAllComment();

        assertAll(
                () -> assertNotNull(commentList),
                () -> assertFalse(commentList.isEmpty()),
                () -> assertEquals(1, commentList.size()),
                () -> assertTrue(commentList.contains(commentCompany)),
                () -> assertFalse(commentList.contains(commentSecond))
        );
    }

    @Test
    @DisplayName("Test CommentRepository method findAllUserComments")
    void findAllUserComments() {

        entityManager.persist(commentOne);
        entityManager.persist(commentSecond);
        entityManager.persist(commentCompany);

        List<Comment> commentList = commentRepository.findAllUserComments(customer.getUserId());

        assertAll(
                () -> assertNotNull(commentList),
                () -> assertFalse(commentList.isEmpty()),
                () -> assertEquals(3, commentList.size()),
                () -> assertTrue(commentList.contains(commentCompany)),
                () -> assertTrue(commentList.contains(commentSecond))
        );

        List<Comment> commentListEmpty = commentRepository.findAllUserComments(customer.getUserId()+2);

        assertTrue(commentListEmpty.isEmpty());
    }

    @Test
    @DisplayName("Test CommentRepository method findAllProductComments")
    void findAllProductComments() {

        List<Comment> commentListEmpt = commentRepository.findAllProductComments(productUA.getProductId());
        assertTrue(commentListEmpt.isEmpty());

        entityManager.persist(commentOne);
        entityManager.persist(commentSecond);

        List<Comment> commentList = commentRepository.findAllProductComments(productUA.getProductId());

        assertAll(
                () -> assertNotNull(commentList),
                () -> assertFalse(commentList.isEmpty()),
                () -> assertEquals(2, commentList.size()),
                () -> assertFalse(commentList.contains(commentCompany)),
                () -> assertTrue(commentList.contains(commentSecond))
        );

        List<Comment> commentListEmpty = commentRepository.findAllProductComments(productUA.getProductId()+2);

        assertTrue(commentListEmpty.isEmpty());
    }

    @Test
    @DisplayName("Test CommentRepository method addProductComment")
    void addProductComment() {

        commentRepository.addProductComment(
                commentOne.getMessage(),
                commentOne.getNote().toString(),
                commentOne.getCustomer().getUserId(),
                commentOne.getProduct().getProductId(),
                commentOne.getProductRating()
                );

        List<Comment> commentList = commentRepository.findAllProductComments(productUA.getProductId());

        assertAll(
                () -> assertNotNull(commentList),
                () -> assertFalse(commentList.isEmpty()),
                () -> assertEquals(1, commentList.size()),
                () -> assertFalse(commentList.contains(commentCompany))
        );
    }

    @Test
    @DisplayName("Test CommentRepository method getProductRating")
    void getProductRating() {

        entityManager.persist(commentOne);
        entityManager.persist(commentSecond);

        Float rating = commentRepository.getProductRating(productUA.getProductId());

        assertEquals(3.65F,rating);
    }
}