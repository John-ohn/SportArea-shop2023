package com.sportArea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "itemId")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductUA product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnore
    private Order order;

    @Column(name = "productQuantity")
    private Integer productQuantity;

    @Column(name = "totalPrice")
    private BigDecimal productTotalPrice;

    public void addOrderToOrderItem(Order order){
        this.order=order;
        order.getProducts().add(this);
    }

}
