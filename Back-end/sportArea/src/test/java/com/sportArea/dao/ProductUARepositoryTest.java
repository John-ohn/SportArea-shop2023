package com.sportArea.dao;

import com.sportArea.entity.ProductUA;
import com.sportArea.entity.TargetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class ProductUARepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductUARepository productUARepository;

    private ProductUA productUA;

    @BeforeEach
    void createProduct() {
        productUA = ProductUA.builder()
                .productName("Solgar Vitamin E")
                .brands("Solgar")
                .type("Харчові добавки, Вітаміни")
                .subtype("Вітамін Е")
                .formOfIssue("Капсул")
                .producingCountry("Україна")
                .taste("Без смаку")
                .price(BigDecimal.valueOf(299))
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
    }

    @Test
    @DisplayName("Test ProductUARepository method findAll")
    void testMethodFindAll(){

        ProductUA  productTwo = ProductUA.builder()
                .productName("Solgar Vitamin E")
                .brands("Solgar")
                .type("Харчові добавки, Вітаміни")
                .subtype("Вітамін Е")
                .formOfIssue("Капсул")
                .producingCountry("Україна")
                .taste("Без смаку")
                .price(BigDecimal.valueOf(299))
                .promotionPrice(BigDecimal.valueOf(270))
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
                .promotion(1)
                .numberOfOrders(5L)
                .dateCreation(LocalDateTime.of(2023, 8, 5, 14, 47, 58))
                .urlImage("https://allnutrition.ua/produkt_img/f8b0i3189_d1200x1200.png")
                .build();

        ProductUA  productThree = ProductUA.builder()
                .productName("Biotech Protein Power")
                .brands("Biotech")
                .type("Протеїн")
                .subtype("Сироватковий протеїн")
                .formOfIssue("Капсул")
                .producingCountry("Україна")
                .taste("Без смаку")
                .price(BigDecimal.valueOf(299))
                .promotionPrice(BigDecimal.valueOf(270))
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
                .promotion(1)
                .numberOfOrders(5L)
                .dateCreation(LocalDateTime.of(2023, 8, 5, 14, 47, 58))
                .urlImage("https://allnutrition.ua/produkt_img/f8b0i3189_d1200x1200.png")
                .build();

        List<ProductUA> emptyList = productUARepository.findAll();

        assertAll(
                ()-> assertTrue(emptyList.isEmpty())
        );

        entityManager.persist(productUA);
        entityManager.persist(productTwo);
        entityManager.persist(productThree);

        ProductUA  productEmpty = ProductUA.builder().build();

        List<ProductUA> productList  = productUARepository.findAll();

        assertAll(
                ()-> assertNotNull(productList),
                ()-> assertEquals(3, productList.size()),
                ()-> assertTrue(productList.contains(productUA)),
                ()-> assertTrue(productList.contains(productTwo)),
                ()-> assertTrue(productList.contains(productThree)),
                ()->assertFalse(productList.contains(productEmpty))
        );
    }

    @Test
    @DisplayName("Test ProductUARepository method findById")
    void testMethodFindById(){
        entityManager.persist(productUA);
        Optional<ProductUA> product  = productUARepository.findById(productUA.getProductId());

        Optional<ProductUA> productEmpty  = productUARepository.findById(8L);

        assertAll(
                ()-> assertTrue(product.isPresent()),
                ()-> assertEquals(productUA, product.get()),
                ()-> assertEquals(productUA.getProductId(), product.get().getProductId())
        );

        assertAll(
                ()-> assertFalse(productEmpty.isPresent()),
                ()-> assertTrue(productEmpty.isEmpty())
        );
    }

    @Test
    @DisplayName("Test ProductUARepository method delete")
    void testMethodDelete() {
        entityManager.persist(productUA);

        ProductUA product = entityManager.find(ProductUA.class, productUA.getProductId());
        productUARepository.delete(product);

        ProductUA productDelete = entityManager.find(ProductUA.class, productUA.getProductId());

        List<ProductUA> list = productUARepository.findAll();

        assertAll(
                () -> assertNull(productDelete),
                () -> assertTrue(list.isEmpty())

        );
    }

    @Test
    @DisplayName("Test ProductUARepository method searchByKeyWordInDescription")
    void testMethodSearchByKeyWordInDescription() {
        entityManager.persist(productUA);

        List<ProductUA> product = productUARepository.searchByKeyWordInDescription("компонентів продукту");

        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(1, product.size()),
                () -> assertTrue(product.contains(productUA))
        );

        List<ProductUA> productEmpty = productUARepository.searchByKeyWordInDescription("Null");

        assertAll(
                () -> assertTrue(productEmpty.isEmpty())
        );
    }

    @Test
    void sortByPriceAscKeyWordDescription() {
    }

    @Test
    void sortByPriceDescKeyWordDescription() {
    }

    @Test
    void sortByRatingDescKeyWordDescription() {
    }

    @Test
    void sortByProductNameAscKeyWordDescription() {
    }

    @Test
    void sortByNumberOfOrdersDescKeyWordDescription() {
    }

    @Test
    void sortByPriceBetweenKeyWordDescription() {
    }

    @Test
    void sortByTimeKeyWordDescription() {
    }

    @Test
    @DisplayName("Test ProductUARepository method searchByKeyWordInTypeSubtype")
    void testMethodSearchByKeyWordInTypeSubtype() {
        entityManager.persist(productUA);

        List<ProductUA> productType = productUARepository.searchByKeyWordInTypeSubtype("Вітаміни");

        assertAll(
                () -> assertNotNull(productType),
                () -> assertEquals(1, productType.size()),
                () -> assertTrue(productType.contains(productUA))
        );

        List<ProductUA> productSubType= productUARepository.searchByKeyWordInTypeSubtype("Вітамін Е");

        assertAll(
                () -> assertNotNull(productSubType),
                () -> assertEquals(1, productSubType.size()),
                () -> assertTrue(productSubType.contains(productUA))
        );

        List<ProductUA> productEmpty= productUARepository.searchByKeyWordInTypeSubtype("Null");

        assertAll(
                () -> assertTrue(productEmpty.isEmpty())
        );
    }

    @Test
    @DisplayName("Test ProductUARepository method searchByPromotionPrice")
    void testMethodSearchByPromotionPrice() {
        ProductUA  productPromotion = ProductUA.builder()
                .productName("Solgar Vitamin E")
                .brands("Solgar")
                .type("Харчові добавки, Вітаміни")
                .subtype("Вітамін Е")
                .formOfIssue("Капсул")
                .producingCountry("Україна")
                .taste("Без смаку")
                .price(BigDecimal.valueOf(299))
                .promotionPrice(BigDecimal.valueOf(270))
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
                .promotion(1)
                .numberOfOrders(5L)
                .dateCreation(LocalDateTime.of(2023, 8, 5, 14, 47, 58))
                .urlImage("https://allnutrition.ua/produkt_img/f8b0i3189_d1200x1200.png")
                .build();
        entityManager.persist(productUA);
        entityManager.persist(productPromotion);

        List<ProductUA> productList = productUARepository.searchByPromotionPrice();

        assertAll(
                () -> assertNotNull(productList),
                () -> assertEquals(1, productList.size()),
                () -> assertTrue(productList.contains(productPromotion)),
                ()-> assertFalse(productList.contains(productUA))
        );

        entityManager.remove(productUA);
        entityManager.remove(productPromotion);

        List<ProductUA> productEmpty= productUARepository.searchByPromotionPrice();

        assertAll(
                () -> assertTrue(productEmpty.isEmpty())
        );
    }
}