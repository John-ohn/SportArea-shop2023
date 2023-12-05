package com.sportArea.controller;

import com.sportArea.entity.dto.DeliveryAddressDTO;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery/address")
public class DeliveryAddressController {

    private final GeneralLogg generalLogg;

    private final DeliveryAddressService deliveryAddressService;

    @Autowired
    public DeliveryAddressController(DeliveryAddressService deliveryAddressService, GeneralLogg generalLogg) {
        this.deliveryAddressService = deliveryAddressService;
        this.generalLogg=generalLogg;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<DeliveryAddressDTO> getAddressByUserId(@PathVariable("userId") Long userId) {
        DeliveryAddressDTO deliveryAddress = deliveryAddressService.findByUserId(userId);

        generalLogg.getLoggerControllerInfo("DeliveryAddressController",
                "getAddressByUserId",
                "/users/{userId}",
                "User Delivery Address");

        return ResponseEntity.ok(deliveryAddress);
    }

    @PostMapping
    public ResponseEntity<String> addDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) {

        deliveryAddressService.save(deliveryAddressDTO);

        generalLogg.getLoggerControllerInfo("DeliveryAddressController",
                "addDeliveryAddress",
                "delivery/address",
                "message (Your delivery address was successful added.) and Save new Delivery Address.");

        return new ResponseEntity<>("Your delivery address was successful added.", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateDeliveryAddress(@RequestBody DeliveryAddressDTO deliveryAddressDTO) {

        deliveryAddressService.save(deliveryAddressDTO);

        generalLogg.getLoggerControllerInfo("DeliveryAddressController",
                "updateDeliveryAddress",
                "delivery/address",
                "message (Your delivery address was successful updated.) and update Delivery Address with deliveryId: "+deliveryAddressDTO.getDeliveryId());

        return new ResponseEntity<>("Your delivery address was successful updated.", HttpStatus.CREATED);
    }
}
