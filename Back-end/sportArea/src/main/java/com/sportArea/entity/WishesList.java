package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "WishesList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishesList {

    @Id
    @Column(name = "wishesId")
    private Long wishesId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductUA product;
}
