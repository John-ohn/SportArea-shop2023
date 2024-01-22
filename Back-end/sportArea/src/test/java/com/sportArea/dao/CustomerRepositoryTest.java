package com.sportArea.dao;

import com.sportArea.entity.Customer;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private CustomerRepository customerRepository;

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

        entityManager.persist(customerOne);

        Customer customer = customerRepository.findByEmail("sewewt@code.com").get();

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

        Customer customer = customerRepository.findByUserId(customerOne.getUserId()).get();

        assertAll(
                () -> assertNotNull(customer),
                () -> assertEquals(customer.getEmail(), customerOne.getEmail()),
                () -> assertEquals(customer.getUserId(), customerOne.getUserId()),
                () -> assertEquals(customer.getLastName(), customerOne.getLastName())
        );
    }


    @Test
    @DisplayName("Test UserRepository method findAll")
    void findAll() {

        Customer customerTwo = new Customer();
        customerTwo.setFirstName("Sewen");
        customerTwo.setLastName("Rasul");
        customerTwo.setEmail("srasult@code.com");
        customerTwo.setPhoneNumber("380342312765");
        customerTwo.setPassword("As123erty87");
        customerTwo.setRole(Role.ROLE_USER);
        customerTwo.setStatus(Status.ACTIVE);
        customerTwo.setTypeRegistration(TypeRegistration.FORM_REGISTRATION);

        Customer customerThree = new Customer();
        customerThree.setFirstName("Lekir");
        customerThree.setLastName("Dev");
        customerThree.setEmail("devlt@code.com");
        customerThree.setPhoneNumber("380342312765");
        customerThree.setPassword("As123erty87");
        customerThree.setRole(Role.ROLE_USER);
        customerThree.setStatus(Status.ACTIVE);
        customerThree.setTypeRegistration(TypeRegistration.FORM_REGISTRATION);

        entityManager.persist(customerOne);
        entityManager.persist(customerTwo);
        entityManager.persist(customerThree);

        List<Customer> customerList = customerRepository.findAll();

        assertAll(
                () -> assertNotNull(customerList),
                () -> assertThat(customerList.size()).isEqualTo(3),
                () -> assertTrue(customerList.contains(customerThree))
        );
    }


    @Test
    @DisplayName("Test UserRepository method save")
    void save() {

        customerRepository.save(customerOne);

        Customer customer = entityManager.find(Customer.class, customerOne.getUserId());

        assertAll(
                () -> assertNotNull(customer),
                () -> assertEquals(customerOne.getFirstName(), customer.getFirstName()),
                () -> assertEquals(customerOne.getUserId(), customer.getUserId())
        );
    }

    @Test
    @DisplayName("Test UserRepository method delete")
    void testMethodDelete() {
        entityManager.persist(customerOne);

        Customer customer = entityManager.find(Customer.class, customerOne.getUserId());

        customerRepository.delete(customer);

        Customer customerDelete = entityManager.find(Customer.class, customerOne.getUserId());

        assertAll(
                () -> assertNull(customerDelete)
        );
    }

    @Test
    void updateUserFirstName() {

        entityManager.persist(customerOne);

        entityManager.clear();

        String firstName = "John";

        customerRepository.updateUserFirstName(customerOne.getUserId(), firstName);

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