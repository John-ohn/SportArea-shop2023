package com.sportArea.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketDTO {

    private Long basketId;

    private UserRegistration user;

    private List<BasketItemDTO> products = new ArrayList<>();;

    private Integer productQuantity;

    private BigDecimal basketTotalPrice;
}
