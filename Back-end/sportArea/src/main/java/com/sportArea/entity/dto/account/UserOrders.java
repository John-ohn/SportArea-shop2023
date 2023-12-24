package com.sportArea.entity.dto.account;

import com.sportArea.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrders {

    private Long orderId;

    private List<UserOrderProducts> products;

    private BigDecimal orderTotalPrice;

    private LocalDateTime orderDate;

    private String orderStatus;


}
