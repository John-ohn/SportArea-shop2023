package com.sportArea.entity.dto.account;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {

    private Long itemId;

    private UserOrderProducts product;

    private Integer productQuantity;

    private BigDecimal productTotalPrice;
}
