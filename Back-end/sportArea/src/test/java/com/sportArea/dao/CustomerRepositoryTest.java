package com.sportArea.dao;

import com.sportArea.entity.Customer;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private Customer customerOne;

    @BeforeEach
    void createUserOne() {
        customerOne = Customer.builder()
                .firstName("Ronald")
                .lastName("Serous")
                .email("sewewt@code.com")
                .phoneNumber("380342312275")
                .password("As123ertyuer")
                .role(Role.ROLE_USER)
                .status(Status.ACTIVE)
                .typeRegistration(TypeRegistration.FORM_REGISTRATION)
                .build();

    }

    @Test
    @DisplayName("JUnit test TestEntityManager Not Null")
    public void contextLoads() {
        assertNotNull(entityManager);
    }


    @Test
    @DisplayName("Test UserRepository method findByEmail")
    void findByEmail() {
//       Customer customerOne2 = new Customer();
//        customerOne2.setFirstName("Ronald");
//        customerOne2.setLastName("Serous");
//        customerOne2.setEmail("sewewt@code.com");
//        customerOne2.setPhoneNumber("380342312275");
//        customerOne2.setPassword("As123ertyuer");
//        customerOne2.setRole(Role.ROLE_USER);
//        customerOne2.setStatus(Status.ACTIVE);
//        customerOne2.setTypeRegistration(TypeRegistration.FORM_REGISTRATION);

        entityManager.persist(customerOne);

        Customer customer = userRepository.findByEmail("sewewt@code.com").get();

        assertAll(
                () -> assertNotNull(customer),
                () -> assertEquals(customer.getEmail(), customerOne.getEmail()),
                () -> assertEquals(customer.getFirstName(), customerOne.getFirstName()),
                () -> assertEquals(customer.getUserId(), customerOne.getUserId())
        );
    }

    @Test
    void findByEmailAndFirstName() {


    }

    @Test
    @DisplayName("Test UserRepository method findByUserId")
    void findByUserId() {
        entityManager.persist(customerOne);

        Customer customer = userRepository.findByUserId(customerOne.getUserId()).get();

        assertAll(
                () -> assertNotNull(customer),
                () -> assertEquals(customer.getEmail(), customerOne.getEmail()),
                () -> assertEquals(customer.getUserId(), customerOne.getUserId()),
                () -> assertEquals(customer.getLastName(), customerOne.getLastName())
        );
    }

    @Test
    @DisplayName("Test UserRepository method save")
    void save() {

        userRepository.save(customerOne);

        Customer customer = entityManager.find(Customer.class, customerOne.getUserId());

        assertAll(
                () -> assertNotNull(customer),
                () -> assertEquals(customerOne.getFirstName(), customer.getFirstName()),
                () -> assertEquals(customerOne.getUserId(), customer.getUserId())
        );


    }

    @Test
    void deleteBetweenIds() {


    }

    @Test
    @Transactional
    void updateUserFirstName() {

        entityManager.persist(customerOne);

//        userRepository.save(customerOne);

        String firstName = "John";

        userRepository.updateUserFirstName(customerOne.getUserId(), firstName);

        System.out.println("Update operation completed");

        Customer customer = entityManager.find(Customer.class, customerOne.getUserId());

        assertAll(
                () -> assertNotNull(customer),
                () -> assertEquals(firstName, customer.getFirstName())
        );


    }

    @Test
    void updateUserLastName() {
    }

    @Test
    void updateUserEmail() {
    }

    @Test
    void updateUserPhoneNumber() {
    }

    @Test
    void updateUserPassword() {
    }

    @Test
    void updateUserStatus() {
    }
}