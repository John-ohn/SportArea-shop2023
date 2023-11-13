package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Basket")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basketId")
    private Long basketId;

    @ManyToOne
    @JoinColumn(name = "guestId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private ProductUA productUA;

    @Column(name = "productQuantity")
    private Integer productQuantity;
}
