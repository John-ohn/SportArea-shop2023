package com.sportArea.entity.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderProducts {

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private String urlImage;

    private Long itemId;

    private Integer productQuantity;

    private BigDecimal productTotalPrice;

}
