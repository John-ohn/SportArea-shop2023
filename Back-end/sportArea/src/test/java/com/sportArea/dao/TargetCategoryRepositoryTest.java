package com.sportArea.dao;


import com.sportArea.entity.TargetCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;


//@SpringBootTest
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class TargetCategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TargetCategoryRepository targetCategoryRepository;

    @Test
    @DisplayName("JUnit test TestEntityManager Not Nul")
    public void contextLoads() {
        assertNotNull(entityManager);
    }

    @Test
    @DisplayName("JUnit test for Save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSaveEmployee() {
        TargetCategory category = new TargetCategory();
        category.setNameCategory("New target");
        category.setIconHttp("http://icon.jpg");
        category.setTranslateName("Translet");

        // When : Action of behavious that we are going to test
        TargetCategory saveEmployee = targetCategoryRepository.save(category);

        // Then : Verify the output

        assertAll(
                () -> assertThat(saveEmployee).isNotNull(),
                () -> assertThat(saveEmployee.getCategoryId()).isGreaterThan(0),
                () -> assertThat("New target").isEqualTo(saveEmployee.getNameCategory())
        );

    }

    @Test
    @DisplayName("JUnit test for method FindALL employees operation")
    public void testMethodFindALL() {
        TargetCategory category = new TargetCategory();
        category.setNameCategory("New target");
        category.setIconHttp("http://icon.jpg");
        category.setTranslateName("Translet");

        TargetCategory category2 = new TargetCategory();
        category2.setNameCategory("New target");
        category2.setIconHttp("http://icon.jpg");
        category2.setTranslateName("Translet");

        TargetCategory category3 = new TargetCategory();
        category3.setNameCategory("New target");
        category3.setIconHttp("http://icon.jpg");
        category3.setTranslateName("Translet");

        entityManager.persist(category);
        entityManager.persist(category2);
        entityManager.persist(category3);


        List<TargetCategory> list = targetCategoryRepository.findAll();


        assertThat(list).isNotNull();
        assertThat(list.size()).isEqualTo(3);
    }


}