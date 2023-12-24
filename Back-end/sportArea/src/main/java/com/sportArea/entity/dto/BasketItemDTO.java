package com.sportArea.entity.dto;

import com.sportArea.entity.Basket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItemDTO {

    private Long itemId;

    private BasketDTO basket;

//    private Long userId;

    private ProductUaDTO product;

    private Integer productQuantity;

    private BigDecimal productTotalPrice;

}


