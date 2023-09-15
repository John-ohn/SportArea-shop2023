package com.sportArea.controller;

import com.sportArea.entity.dto.OrderDTO;
import com.sportArea.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @GetMapping("/list")
    public List<OrderDTO> ordersList(){
        return orderService.findAll();
    }

    @PostMapping("/addOrder")
    public ResponseEntity<String> addOrder(@RequestBody OrderDTO order){
        orderService.save(order);

        return ResponseEntity.ok("Your order was added successfully.");

    }


    @GetMapping("/numberOfProductOrders/{productId}")
    public Long numberOfOrders(@PathVariable("productId") Long productId){
        Long count = orderService.numberOfOrders(productId);
        logger.info("From controller -order-. Send count Orders By Product with productId: {}", productId);
        return count;
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId){
        orderService.delete(orderId);

        return ResponseEntity.ok("Order by id: "+orderId+" was deleted successfully.");

    }
}
