package com.sportArea.dao;


import com.sportArea.entity.TargetCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import org.springframework.test.context.TestPropertySource;


import java.util.List;

import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class TargetCategoryRepositoryTestUa {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TargetCategoryRepository targetCategoryRepository;

   private TargetCategory category1;

    @BeforeEach
    void creatTargetCategory() {
        category1 = new TargetCategory();
        category1.setNameCategory("New target");
        category1.setIconHttp("http://icon.jpg");
        category1.setTranslateName("Translet");
    }

    @Test
    @DisplayName("JUnit test TestEntityManager Not Null")
    public void contextLoads() {
        assertNotNull(entityManager);
    }

    @Test
    @DisplayName("JUnit test TargetCategoryRepository  for Save Target Category ")
    public void givenEmployeeObject_whenSave_thenReturnSaveEmployee() {

        // When : Action of behavious that we are going to test
        TargetCategory saveCategory = targetCategoryRepository.save(category1);

        TargetCategory invalidCategory = new TargetCategory();
        invalidCategory.setNameCategory(null);

        // Then : Verify the output

        assertAll(
                () -> assertThat(saveCategory).isNotNull(),
                () -> assertThat(saveCategory.getCategoryId()).isGreaterThan(0),
                () -> assertThat(category1.getNameCategory()).isEqualTo(saveCategory.getNameCategory())
        );

        // Test if throw Exception if NameCategory is null.
        assertThrows(Exception.class, () -> targetCategoryRepository.save(invalidCategory));
    }

    @Test
    @DisplayName("JUnit test TargetCategoryRepository for method FindALL Target Category ")
    public void testMethodFindALL() {

        TargetCategory category2 = new TargetCategory();
        category2.setNameCategory("New target");
        category2.setIconHttp("http://icon.jpg");
        category2.setTranslateName("Translet");

        TargetCategory category3 = new TargetCategory();
        category3.setNameCategory("New target");
        category3.setIconHttp("http://icon.jpg");
        category3.setTranslateName("Translet");

        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.persist(category3);

        List<TargetCategory> listCategory = targetCategoryRepository.findAll();

        assertAll(
                () -> assertFalse(listCategory.isEmpty()),
                () -> assertThat(listCategory.size()).isEqualTo(3),
                () -> assertThat(listCategory.get(0).getCategoryId()).isGreaterThan(0)
        );

        entityManager.remove(listCategory.get(0));
        entityManager.remove(listCategory.get(1));
        entityManager.remove(listCategory.get(2));

        List<TargetCategory> emptyList = targetCategoryRepository.findAll();

        assertTrue(emptyList.isEmpty());
    }

    @Test
    @DisplayName("JUnit test TargetCategoryRepository for method FindById Target Category ")
    public void testMethodFindById() {

        TargetCategory category2 = new TargetCategory();
        category2.setNameCategory("New target");
        category2.setIconHttp("http://icon.jpg");
        category2.setTranslateName("Translet");

        TargetCategory saveCategory1 = entityManager.persist(category1);
        TargetCategory saveCategory2 = entityManager.persist(category2);

        Optional<TargetCategory> category = targetCategoryRepository.findById(saveCategory1.getCategoryId());

        assertAll(
                () -> assertThat(category.get()).isNotNull(),
                () -> assertThat(category.get()).isEqualTo(category1),
                () -> assertEquals(saveCategory1.getCategoryId(), category.get().getCategoryId()),
                () -> assertEquals("New target", category.get().getNameCategory()),
                () -> assertEquals("Translet", category.get().getTranslateName())
        );

        Optional<TargetCategory> getCategory2 = targetCategoryRepository.findById(saveCategory2.getCategoryId());

        assertAll(
                () -> assertThat(getCategory2.get()).isNotNull(),
                () -> assertThat(getCategory2.get()).isEqualTo(category2),
                () -> assertEquals(category2.getCategoryId(), getCategory2.get().getCategoryId())

        );

        Optional<TargetCategory> getCategory4 = targetCategoryRepository.findById(4L);

        assertAll(
                () -> assertTrue(getCategory4.isEmpty())
        );
    }

    @Test
    @DisplayName("JUnit test TargetCategoryRepository for method delete Target Category")
    public void testMethodDelete() {

        TargetCategory saveCategory = entityManager.persist(category1);

        targetCategoryRepository.delete(saveCategory);

        Optional<TargetCategory> getCategory = targetCategoryRepository.findById(saveCategory.getCategoryId());

        List<TargetCategory> list = targetCategoryRepository.findAll();

        assertAll(
                () -> assertTrue(getCategory.isEmpty()),
                () -> assertTrue(list.isEmpty())
        );
    }


}