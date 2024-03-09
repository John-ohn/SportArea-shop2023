package com.sportArea.entity.dto;

import com.sportArea.entity.DeliveryAddress;
import com.sportArea.entity.OrderInfo;
import com.sportArea.entity.OrderItem;
import com.sportArea.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;

    private OrderInfo orderInfo;

    private String paymentMethod;

    private Integer amount;

    private BigDecimal orderTotalPrice;

    private LocalDateTime orderDate;

    private OrderStatus orderStratus;

    private List<OrderItem> products;


}
