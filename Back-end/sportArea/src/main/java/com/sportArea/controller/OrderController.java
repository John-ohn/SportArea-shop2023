package com.sportArea.controller;

import com.sportArea.entity.Order;
import com.sportArea.entity.dto.OrderDTO;
import com.sportArea.entity.dto.account.UserOrders;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final GeneralLogg generalLogg ;

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService, GeneralLogg generalLogg ) {
        this.orderService = orderService;
        this.generalLogg=generalLogg;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UserOrders>> findByUserId(@PathVariable("userId") Long userId) {
        List<UserOrders> orderList = orderService.allUserOrders(userId);

        generalLogg.getLoggerControllerInfo("OrderController",
                "ordersList",
                "/users/{userId}",
                "List of Orders or empty list");

        return ResponseEntity.ok(orderList);
    }



//    @GetMapping
//    public ResponseEntity<List<OrderDTO>> ordersList() {
//        List<OrderDTO> orderList = orderService.findAll();
//        logger.info("From OrderController controller -ordersList- /orders. Return List of Orders.");
//
//        return ResponseEntity.ok(orderList);
//    }

//    @PostMapping
//    public ResponseEntity<String> addOrder(@RequestBody OrderDTO order) {
//        orderService.save(order);
//        logger.info("From OrderController controller -addOrder-  /orders . Save new order.");
//        return ResponseEntity.ok("Your order was added successfully.");
//    }

//    @GetMapping("/amount/{productId}")
//    public Long numberOfOrders(@PathVariable("productId") Long productId) {
//        Long count = orderService.numberOfOrders(productId);
//        logger.info("From OrderController controller -numberOfOrders- /orders/amount/{productId}. Send count Orders By Product with productId: {}", productId);
//        return count;
//    }
//
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
//        orderService.delete(orderId);
//        logger.info("From OrderController controller -deleteOrder- /orders/{orderId}. Delete Orders By orderId: {}", orderId);
//        return ResponseEntity.ok("Order by id: " + orderId + " was deleted successfully.");
//
//    }
}
