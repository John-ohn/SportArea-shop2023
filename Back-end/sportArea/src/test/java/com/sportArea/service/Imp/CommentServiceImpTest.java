package com.sportArea.service.Imp;

import com.sportArea.dao.CommentRepository;
import com.sportArea.entity.*;
import com.sportArea.entity.dto.AddComment;
import com.sportArea.entity.dto.CommentDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.CustomerService;
import com.sportArea.service.ProductUAService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImpTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductUAService productUAService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImp commentServiceImp;

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @Captor
    private ArgumentCaptor<String> noteCaptor;

    @Captor
    private ArgumentCaptor<Long> userIdCaptor;

    @Captor
    private ArgumentCaptor<Long> productIdCaptor;

    @Captor
    private ArgumentCaptor<Float> productRatingCaptor;

    private Comment commentCompany;

    private Comment commentProduct;

    private Customer customerOne;

    private ProductUA productUA;

    @BeforeEach
    void createComment() {

        customerOne = Customer.builder()
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
                .rating(5F)
                .status("В наявності")
                .promotion(0)
                .numberOfOrders(5L)
                .dateCreation(LocalDateTime.of(2023, 8, 5, 14, 47, 58))
                .urlImage("https://allnutrition.ua/produkt_img/f8b0i3189_d1200x1200.png")
                .build();

        commentCompany = Comment.builder()
                .commentId(1L)
                .userName("Oleg")
                .message("Класний магазин рекомендую")
                .note(Note.FOR_COMPANY)
                .dateTime(LocalDateTime.of(2023, 9, 8, 15, 47, 30))
                .customer(customerOne)
                .build();


        commentProduct = Comment.builder()
                .commentId(2L)
                .userName("Oleg")
                .message("Класний продукт рекомендую")
                .note(Note.FOR_PRODUCT)
                .dateTime(LocalDateTime.of(2023, 8, 8, 15, 47, 30))
                .productRating(3.44F)
                .customer(customerOne)
                .product(productUA)
                .build();

    }


    @Test
    @DisplayName("Test CommentServiceImp method findById")
    void testMethodFindById() {

        when(commentRepository.findById(commentCompany.getCommentId())).thenReturn(Optional.ofNullable(commentCompany));

        CommentDTO commentDTO = commentServiceImp.findById(commentCompany.getCommentId());

        assertAll(
                () -> assertNotNull(commentDTO),
                () -> assertEquals(commentCompany.getCommentId(), commentDTO.getCommentId())
        );

        GeneralException error = assertThrows(GeneralException.class, () ->
                commentServiceImp.findById(commentCompany.getCommentId() + 2));

//        assertThrows(GeneralException.class, () -> commentServiceImp.findById(commentCompany.getCommentId() + 2));
        assertEquals("Comment with commentId: " + (commentCompany.getCommentId() + 2) + " is not available.", error.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());

        verify(commentRepository, times(2)).findById(any());
    }

    @Test
    @DisplayName("Test CommentServiceImp method findAllUserComments")
    void testMethodFindAllUserComments() {
        List<Comment> commentList = List.of(commentCompany, commentProduct);

        when(commentRepository.findAllUserComments(customerOne.getUserId())).thenReturn(commentList);

        List<CommentDTO> commentDTOList = commentServiceImp.findAllUserComments(customerOne.getUserId());

        assertAll(
                () -> assertNotNull(commentDTOList),
                () -> assertFalse(commentDTOList.isEmpty()),
                () -> assertEquals(2, commentDTOList.size()),
                () -> assertTrue(commentDTOList.stream().anyMatch(a -> a.getNote().equals(Note.FOR_COMPANY))),
                () -> assertTrue(commentDTOList.stream().anyMatch(a -> a.getNote().equals(Note.FOR_PRODUCT)))
        );

        GeneralException error = assertThrows(GeneralException.class, () -> commentServiceImp.findAllUserComments(customerOne.getUserId() + 2));

        assertEquals("User with userId: " + (customerOne.getUserId() + 2) + " is not available.", error.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());

        verify(commentRepository, times(2)).findAllUserComments(any());
    }

    @Test
    @DisplayName("Test CommentServiceImp method findAllProductComments")
    void testMethodFindAllProductComments() {

        List<Comment> commentList = List.of(commentProduct);

        when(commentRepository.findAllProductComments(productUA.getProductId())).thenReturn(commentList);

        List<CommentDTO> commentDTOList = commentServiceImp.findAllProductComments(productUA.getProductId());

        assertAll(
                () -> assertNotNull(commentDTOList),
                () -> assertFalse(commentDTOList.isEmpty()),
                () -> assertEquals(1, commentDTOList.size()),
                () -> assertTrue(commentDTOList.stream().anyMatch(a -> Objects.equals(a.getCommentId(), commentProduct.getCommentId()))),
                () -> assertTrue(commentDTOList.stream().anyMatch(a -> a.getNote().equals(Note.FOR_PRODUCT)))
        );

        GeneralException error = assertThrows(GeneralException.class, () -> commentServiceImp.findAllProductComments(productUA.getProductId() + 2));

        assertEquals("Comment with productId: " + (productUA.getProductId() + 2) + " is not available. Product don't have comments.", error.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());

        verify(commentRepository, times(2)).findAllProductComments(any());

    }

    @Test
    @DisplayName("Test CommentServiceImp method findAll")
    void testMethodFindAll() {

        List<Comment> commentList = List.of(commentProduct, commentCompany);

        when(commentRepository.findAll()).thenReturn(commentList);

        List<CommentDTO> commentDTOList = commentServiceImp.findAll();

        assertAll(
                () -> assertNotNull(commentDTOList),
                () -> assertFalse(commentDTOList.isEmpty()),
                () -> assertEquals(2, commentDTOList.size()),
                () -> assertTrue(commentDTOList.stream().anyMatch(a -> a.getNote().equals(Note.FOR_COMPANY))),
                () -> assertTrue(commentDTOList.stream().anyMatch(a -> a.getNote().equals(Note.FOR_PRODUCT)))
        );

        List<Comment> commentListEmpty = new ArrayList<>();

        when(commentRepository.findAll()).thenReturn(commentListEmpty);

        GeneralException error = assertThrows(GeneralException.class, () -> commentServiceImp.findAll());

        assertEquals("Comments is not available. The list of comments is empty.", error.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());

        verify(commentRepository, times(2)).findAll();
    }

    @Test
    @DisplayName("Test CommentServiceImp method delete")
    void testMethodDelete() {

        Long responseId = 1L;
        Comment mockComment = new Comment();


        when(commentRepository.findById(responseId)).thenReturn(Optional.of(mockComment));

        commentServiceImp.delete(responseId);

        verify(commentRepository).delete(mockComment);


        when(commentRepository.findById(responseId)).thenReturn(Optional.empty());

        GeneralException error = assertThrows(GeneralException.class, () -> commentServiceImp.delete(responseId));

        assertEquals("Comment with responseId: " + responseId + " is not available.", error.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, error.getHttpStatus());
    }

    @Test
    @DisplayName("Test CommentServiceImp method findCompanyComments")
    void findCompanyComments() {
        List<Comment> commentList = List.of(commentCompany);

        when(commentRepository.findCompanyComments()).thenReturn(commentList);

        List<CommentDTO> companyCommentsList = commentServiceImp.findCompanyComments();

        assertAll(
                () -> assertNotNull(companyCommentsList),
                () -> assertFalse(companyCommentsList.isEmpty()),
                () -> assertEquals(1, companyCommentsList.size())
        );


        when(commentRepository.findCompanyComments()).thenReturn(new ArrayList<>());

        GeneralException error = assertThrows(GeneralException.class, () -> commentServiceImp.findCompanyComments());

        assertEquals("Company Comments  List is empty.", error.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, error.getHttpStatus());
    }

    @Test
    void deleteResponseIdsBetweenIds() {


    }

    @Test
    @DisplayName("Test CommentServiceImp method addProductComment")
    void addProductComment() {

        AddComment comment = new AddComment();
        comment.setMessage(commentProduct.getMessage());
        comment.setUserId(commentProduct.getCustomer().getUserId());
        comment.setProductId(commentProduct.getProduct().getProductId());
        comment.setNote(commentProduct.getNote());
        comment.setProductRating(commentProduct.getProduct().getRating());

        when(commentRepository.getProductRating(1L)).thenReturn(5F);
        when(productUAService.findByIdWithoutDTO(1L)).thenReturn(productUA);
        Mockito.doNothing().when(productUAService).saveWithoutDTO(Mockito.any());

        commentServiceImp.addProductComment(comment);

        verify(commentRepository).addProductComment(
                messageCaptor.capture(),
                noteCaptor.capture(),
                userIdCaptor.capture(),
                productIdCaptor.capture(),
                productRatingCaptor.capture()
        );

        assertAll(
                () -> assertEquals("Класний продукт рекомендую", messageCaptor.getValue()),
                () -> assertEquals(Note.FOR_PRODUCT.toString(), noteCaptor.getValue()),
                () -> assertEquals(1L, userIdCaptor.getValue()),
                () -> assertEquals(1L, productIdCaptor.getValue()),
                () -> assertEquals(5F, productRatingCaptor.getValue())
        );

    }

    @Test
    void createCommentFromCommentDTO() {

    }

    @Test
    @DisplayName("Test CommentServiceImp method createCommentDTOFromComment")
    void createCommentDTOFromComment() {

        CommentDTO commentDTO = commentServiceImp.createCommentDTOFromComment(commentProduct);

        assertAll(
                () -> assertNotNull(commentDTO),
                () -> assertEquals(commentProduct.getMessage(), commentDTO.getMessage()),
                () -> assertEquals(commentProduct.getProductRating(), commentDTO.getProductRating()),
                () -> assertEquals(commentProduct.getCommentId(), commentDTO.getCommentId())
        );
    }

    @Test
    @DisplayName("Test CommentServiceImp method convertToCommentDTOList")
    void convertToCommentDTOList() {
        List<Comment> commentList = List.of(commentCompany,commentProduct);

        List<CommentDTO> commentDTOList = commentServiceImp.convertToCommentDTOList(commentList);

        assertAll(
                () -> assertFalse(commentDTOList.isEmpty()),
                () -> assertEquals(2,commentDTOList.size() )

        );
    }

    @Test
    @DisplayName("Test CommentServiceImp method addProductRating")
    void addProductRating() {
        Long productId = 1L;
        when(commentRepository.getProductRating(productId)).thenReturn(5F);
        when(productUAService.findByIdWithoutDTO(productId)).thenReturn(productUA);
        Mockito.doNothing().when(productUAService).saveWithoutDTO(Mockito.any());

        commentServiceImp.addProductRating(productId);

        verify(commentRepository).getProductRating(productId);
        verify(productUAService).findByIdWithoutDTO(productId);
        verify(productUAService).saveWithoutDTO(productUA);
    }
}