package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Ordering")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "orderId")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "orderInfoId")
    private OrderInfo orderInfo;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "totalPrice")
    private BigDecimal orderTotalPrice;

    @Column(name = "orderDate")
    private LocalDateTime orderDate;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "orderStatus")
    private OrderStatus orderStatus;


    @OneToMany(mappedBy = "order")
    private List<OrderItem> products;

    public void addOrderItemToOrder(OrderItem orderItem){
        orderItem.setOrder(this);
        this.products.add(orderItem);
    }
}
