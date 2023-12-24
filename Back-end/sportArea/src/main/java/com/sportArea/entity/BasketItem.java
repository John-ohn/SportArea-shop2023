package com.sportArea.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BasketItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private Long itemId;

//    @Column(name = "userId")
//    private Long userId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductUA product;

    @ManyToOne
    @JoinColumn(name = "basketId")
    private Basket basket;

    @Column(name = "productQuantity")
    private Integer productQuantity;

    @Column(name = "totalPrice")
    private BigDecimal productTotalPrice;

    public void addBasketToBasketItem(Basket basket){
        this.basket=basket;
        basket.getProducts().add(this);
    }
}
