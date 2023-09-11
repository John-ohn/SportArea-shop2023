package com.sportArea.controller;

import com.sportArea.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @GetMapping("/numberOfProductOrders/{productId}")
    public Long numberOfOrders(@PathVariable("productId") Long productId){
        Long count = orderService.numberOfOrders(productId);
        logger.info("From controller -order-. Send count Orders By Product with productId: {}", productId);
        return count;
    }
}
