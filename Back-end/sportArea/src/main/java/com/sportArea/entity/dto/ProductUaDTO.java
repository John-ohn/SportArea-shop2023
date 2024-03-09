package com.sportArea.entity.dto;


import com.sportArea.entity.ProductEN;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUaDTO {

    private Long productId;

    private String productName;

    private String brands;

    private String type;

    private String subtype;

    private String formOfIssue;

    private String producingCountry;

    private String taste;

    private BigDecimal price;

    private BigDecimal promotionPrice;

    private String currency;

    private String weight;

    private Long article;

    private Integer productAmount;

    private String description;

    private String productConsist;

    private Float rating;

    private String status;

    private Integer promotion;

    private Long numberOfOrders;

    private LocalDateTime dateCreation;

    private String urlImage;

    private ProductEN productEN;
}
