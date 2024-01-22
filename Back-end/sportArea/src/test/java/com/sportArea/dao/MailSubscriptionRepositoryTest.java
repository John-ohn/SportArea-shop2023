package com.sportArea.dao;

import com.sportArea.entity.MailSubscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class MailSubscriptionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MailSubscriptionRepository mailSubscriptionRepository;

    private MailSubscription mailSubscription;

    @BeforeEach
    void createMailSubscription(){
        mailSubscription=new MailSubscription();
        mailSubscription.setEmail("code@gmail.com");
    }

    @Test
    @DisplayName("Test MailSubscriptionRepository method save")
    void testMethodSave (){
        mailSubscriptionRepository.save(mailSubscription);

        MailSubscription mailSub = entityManager.find(MailSubscription.class,mailSubscription.getSubscriptionId() );

        assertAll(
                ()-> assertNotNull(mailSub),
                ()-> assertEquals("code@gmail.com",mailSub.getEmail()),
                ()-> assertEquals(mailSubscription,mailSub)
        );

        MailSubscription mailSubEmpty = new MailSubscription();

        assertThrows(Exception.class, ()-> mailSubscriptionRepository.save(mailSubEmpty));
    }

    @Test
    @DisplayName("Test MailSubscriptionRepository method findByEmail")
    void testMethodFindByEmail() {
        entityManager.persist(mailSubscription);

        Optional<MailSubscription> mailSub = mailSubscriptionRepository.findByEmail(mailSubscription.getEmail());

        assertAll(
                ()-> assertFalse(mailSub.isEmpty()),
                ()-> assertEquals(mailSubscription.getEmail(), mailSub.get().getEmail())
        );

        Optional<MailSubscription> mailSubEmpty = mailSubscriptionRepository.findByEmail("setEmpty@code.com");

        assertTrue(mailSubEmpty.isEmpty());
    }

    @Test
    @DisplayName("Test MailSubscriptionRepository method existsByEmail")
    void testMethodExistsByEmail() {
        entityManager.persist(mailSubscription);

        boolean mailSub = mailSubscriptionRepository.existsByEmail(mailSubscription.getEmail());
        boolean mailSubEmpty = mailSubscriptionRepository.existsByEmail("setEmpty@code.com");

        assertTrue(mailSub);
        assertFalse(mailSubEmpty);
    }
}