package com.sportArea.service.Imp;

import com.sportArea.dao.OrderRepository;
import com.sportArea.entity.Order;
import com.sportArea.entity.ProductUA;
import com.sportArea.entity.dto.OrderDTO;
import com.sportArea.entity.dto.ProductUaDTO;
import com.sportArea.exception.OrderException;
import com.sportArea.exception.UserException;
import com.sportArea.service.OrderService;
import com.sportArea.service.ProductUAService;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);

    private final UserService userService;

    private final ProductUAService productUAService;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, UserService userService, ProductUAService productUAService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productUAService = productUAService;
    }

    @Override
    public List<OrderDTO> findAll() {

        List<Order> orderList = orderRepository.findAll();

        if (!orderList.isEmpty()) {
            List<OrderDTO> orderDTOListList = convertToOrderDTOList(orderList);
            logger.info("From OrderServiceImp method -findAll- return List of OrderDTOs.");

            return orderDTOListList;
        } else {
            logger.warn("From OrderServiceImp method -findById- send war message " +
                    "( Don't find any Orders. Products list is empty. ({}))", HttpStatus.NOT_FOUND);
            throw new OrderException("Don't find any Orders. Orders list is empty.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<OrderDTO> findAllById(Iterable<Long> longs) {


        return null;
    }

    @Override
    public OrderDTO findById(Long orderId) {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            logger.info("From OrderServiceImp method -findById- return Order by id: {} ", orderId);

            return createOrderDTOFromOrder(order.get());
        } else {
            logger.warn("From OrderServiceImp method -findById- send war message " +
                    "( Order with orderId {} is not available. ({}))", orderId, HttpStatus.NOT_FOUND);
            throw new OrderException("Order with orderId: " + orderId + " is not available.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void save(OrderDTO orderDTO) {
        if (orderDTO != null) {
            Order order = createOrderFromOrderDTO(orderDTO);
            LocalDateTime localDateTime = LocalDateTime.now();
            order.setOrderDate(localDateTime);
            orderRepository.save(order);

            logger.info("From OrderServiceImp method -save- return new message (Order was added successfully.).");

            updateProductNumberOfOrders(order.getProduct().getProductId());

        } else {
            logger.warn("From OrderServiceImp method -save- send war message " +
                    "( Order is not available or his is empty. ({}))", HttpStatus.NOT_FOUND.value());

            throw new OrderException("Order is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Long numberOfOrders(Long productId) {
        if (productId > 0) {

            Long count = orderRepository.countOrdersByProduct(productId);
            logger.info("From OrderServiceImp method -numberOfOrders- return number of count Orders By Product: {}", count);
            return count;

        } else {
            logger.warn("From OrderServiceImp method -numberOfOrders- send war message " +
                    "( Product Id: {} is not available or his is empty. ({}))", productId, HttpStatus.NOT_FOUND.value());

            throw new OrderException("Product Id: " + productId + " is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public <S extends OrderDTO> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public void delete(Long orderId) {

        if ((orderId > 0)) {
            Optional<Order> order = orderRepository.findById(orderId);

            if (order.isPresent()) {
                orderRepository.delete(order.get());
                logger.info("From OrderServiceImp method -delete- return message (Order with orderId: {} was deleted.).",
                        orderId);
                updateProductNumberOfOrders(order.get().getProduct().getProductId());
                logger.info("From OrderServiceImp method -delete- return message ( Update Product Number Of Orders with productId: {}).",
                        order.get().getProduct().getProductId());
            } else {
                logger.warn("From OrderServiceImp method -delete- send war message " +
                        "(Order is not available. ({}) )", HttpStatus.NOT_FOUND.name());
                throw new OrderException("Order is not available.", HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warn("From OrderServiceImp method -delete- send war message " +
                    "Order with orderId:  {} is not available.  {}", orderId, HttpStatus.NOT_FOUND.name());
            throw new OrderException("Order with orderId: " + orderId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    public OrderDTO createOrderDTOFromOrder(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .email(order.getEmail())
                .phoneNumber(order.getPhoneNumber())
                .paymentMethod(order.getPaymentMethod())
                .delivery(order.getDelivery())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .user(userService.createUserDTOFromUser(order.getUser()))
                .product(productUAService.createProductDTOFromProductUA(order.getProduct()))
                .build();
    }

    public Order createOrderFromOrderDTO(OrderDTO order) {
        if(order.getUser()==null){
            return Order.builder()
                    .orderId(order.getOrderId())
                    .firstName(order.getFirstName())
                    .lastName(order.getLastName())
                    .email(order.getEmail())
                    .phoneNumber(order.getPhoneNumber())
                    .paymentMethod(order.getPaymentMethod())
                    .delivery(order.getDelivery())
                    .amount(order.getAmount())
                    .orderDate(order.getOrderDate())
                    .product(productUAService.createProductFromProductUaDTO(order.getProduct()))
                    .build();
        }else {
            return Order.builder()
                    .orderId(order.getOrderId())
                    .firstName(order.getFirstName())
                    .lastName(order.getLastName())
                    .email(order.getEmail())
                    .phoneNumber(order.getPhoneNumber())
                    .paymentMethod(order.getPaymentMethod())
                    .delivery(order.getDelivery())
                    .amount(order.getAmount())
                    .orderDate(order.getOrderDate())
                    .user(userService.createUserFromUserDTO(order.getUser()))
                    .product(productUAService.createProductFromProductUaDTO(order.getProduct()))
                    .build();
        }
    }

    public List<OrderDTO> convertToOrderDTOList(List<Order> orderList) {
        List<OrderDTO> orderDTOList = orderList.stream()
                .map(this::createOrderDTOFromOrder)
                .toList();

        return orderDTOList;
    }

    public void updateProductNumberOfOrders(Long productId) {
        Long count = numberOfOrders(productId);
        ProductUaDTO productUA = productUAService.findById(productId);
        productUA.setNumberOfOrders(count);
        productUAService.save(productUA);
    }

}
