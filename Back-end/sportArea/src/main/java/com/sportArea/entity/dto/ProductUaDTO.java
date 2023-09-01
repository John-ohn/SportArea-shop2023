package com.sportArea.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    private Double price;

    private String currency;

    private Double weight;

    private Long article;

    private Integer productAmount;

    private String description;

    private String productConsist;

    private Integer rating;

    private String status;

    private Integer promotion;
}
