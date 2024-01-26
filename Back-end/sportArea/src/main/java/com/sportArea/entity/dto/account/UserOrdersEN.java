package com.sportArea.entity.dto.account;

import com.sportArea.entity.DeliveryAddress;
import com.sportArea.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrdersEN {

    private Long orderId;

    private List<UserOrderProductEN> products;

    private BigDecimal orderTotalPrice;

    private LocalDate orderDate;

    private DeliveryAddress deliveryAddress;

    private String paymentMethod;

    private String statusText;

    private OrderStatus status;
}
