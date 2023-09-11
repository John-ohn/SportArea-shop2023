package com.sportArea.service;


import com.sportArea.entity.dto.OrderDTO;
import org.springframework.data.domain.Example;

import java.util.List;


public interface OrderService {

    List<OrderDTO> findAll();

    List<OrderDTO> findAllById(Iterable<Long> longs);

    OrderDTO findById(Long orderId);

    void save(OrderDTO order);

    Long numberOfOrders(Long example);

    <S extends OrderDTO> boolean exists(Example<S> example);

    void delete(OrderDTO entity);


}
