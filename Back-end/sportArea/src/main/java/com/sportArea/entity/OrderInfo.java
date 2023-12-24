package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "OrderInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderInfoId")
    private Long orderInfoId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

//     mast be object Cart
//    @Column(name = "paymentMethod")
//    private String paymentMethod;

    @OneToOne
    @JoinColumn(name = "deliveryId")
    private DeliveryAddress delivery;

//    @Column(name = "amount")
//    private Integer amount;

//    @Column(name = "orderDate")
//    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Customer customer;

//    @OneToMany
//    @JoinColumn(name = "productId")
//    private List<ProductUA> product;
}
