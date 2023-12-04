package com.sportArea.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Basket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basketId")
    private Long basketId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "productQuantity")
    private Integer productQuantity;

    @Column(name = "basketTotalPrice")
    private BigDecimal basketTotalPrice;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketItem> products= new ArrayList<>();

    public void addBasketItemToBasket(BasketItem basketItem){
        basketItem.setBasket(this);
        this.products.add(basketItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(basketId, basket.basketId) && Objects.equals(user, basket.user) && Objects.equals(productQuantity, basket.productQuantity) && Objects.equals(basketTotalPrice, basket.basketTotalPrice) && Objects.equals(products, basket.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketId, user, productQuantity, basketTotalPrice, products);
    }
}
