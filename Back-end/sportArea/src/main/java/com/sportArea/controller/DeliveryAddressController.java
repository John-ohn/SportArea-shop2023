package com.sportArea.controller;

import com.sportArea.entity.dto.DeliveryAddressDTO;
import com.sportArea.service.DeliveryAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery/address")
public class DeliveryAddressController {

    Logger logger = LoggerFactory.getLogger(DeliveryAddressController.class);

    private final DeliveryAddressService deliveryAddressService;

    @Autowired
    public DeliveryAddressController(DeliveryAddressService deliveryAddressService) {
        this.deliveryAddressService = deliveryAddressService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<DeliveryAddressDTO> getAddressByUserId(@PathVariable("userId") Long userId) {
        DeliveryAddressDTO deliveryAddress = deliveryAddressService.findByUserId(userId);
        logger.info("From DeliveryAddressController method -getAddressByUserId- /user/{userId}. Return User Delivery Address");
        return ResponseEntity.ok(deliveryAddress);
    }

    @PostMapping
    public ResponseEntity<String> addDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) {

        deliveryAddressService.save(deliveryAddressDTO);
        logger.info("From DeliveryAddressController controller -addDeliveryAddress- /delivery/address . Save new Delivery Address.");

        return new ResponseEntity<>("Your delivery address was successful added .", HttpStatus.CREATED);
    }
}
