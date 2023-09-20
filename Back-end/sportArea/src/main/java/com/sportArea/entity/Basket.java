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

    @Column(name = "guestId")
    private Long guestId;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private ProductUA productUA;
}
