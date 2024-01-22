package com.sportArea.dao;

import com.sportArea.entity.DeliveryAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class DeliveryAddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    private DeliveryAddress deliveryAddress;

    @BeforeEach
    void createDeliveryAddress(){
        deliveryAddress= DeliveryAddress.builder()
                .region("Київська Область")
                .city("Волошно")
                .department("Поштомат №2344222, вул.Корестав 19")
                .build();
    }

    @Test
    @DisplayName("Test DeliveryAddressRepository method findById")
    void testMethodFindById(){

        entityManager.persist(deliveryAddress);

       Optional<DeliveryAddress> findAddress = deliveryAddressRepository.findById(deliveryAddress.getDeliveryId());

       assertAll(
               ()-> assertNotNull(findAddress),
               ()-> assertTrue(findAddress.isPresent()),
               ()-> assertEquals("Волошно", findAddress.get().getCity()),
               ()-> assertEquals(deliveryAddress,findAddress.get())
       );

        Optional<DeliveryAddress> addressEmpty = deliveryAddressRepository.findById(deliveryAddress.getDeliveryId()+2);

        assertTrue(addressEmpty.isEmpty());
        assertThrows(Exception.class, addressEmpty::get);
    }

    @Test
    @DisplayName("Test DeliveryAddressRepository method save")
    void testMethodSave(){
        deliveryAddressRepository.save(deliveryAddress);


        DeliveryAddress findAddress = entityManager.find(DeliveryAddress.class, deliveryAddress.getDeliveryId());

        assertAll(
                ()-> assertNotNull(findAddress),
                ()-> assertFalse(findAddress.getRegion().isEmpty()),
                ()-> assertEquals("Волошно", findAddress.getCity()),
                ()-> assertEquals(deliveryAddress,findAddress)
        );

        Optional<DeliveryAddress> addressEmpty = deliveryAddressRepository.findById(deliveryAddress.getDeliveryId()+2);

        assertTrue(addressEmpty.isEmpty());
        assertThrows(Exception.class, addressEmpty::get);

    }


}