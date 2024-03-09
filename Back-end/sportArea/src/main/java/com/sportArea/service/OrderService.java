package com.sportArea.service;


import com.sportArea.entity.Order;
import com.sportArea.entity.dto.OrderDTO;
import com.sportArea.entity.dto.account.UserOrders;
import org.springframework.data.domain.Example;

import java.util.List;


public interface OrderService {

//    List<OrderDTO> findAll();

//    List<OrderDTO> findAllById(Iterable<Long> longs);

    Order findByUserId(Long userId);

    UserOrders allUserOrders(Long userId);

//    void save(OrderDTO order);
//
//    Long numberOfOrders(Long example);
//
//    <S extends OrderDTO> boolean exists(Example<S> example);
//
//    void delete( Long orderId);


}
