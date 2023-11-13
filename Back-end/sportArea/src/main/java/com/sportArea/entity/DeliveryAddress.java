package com.sportArea.entity;

import javax.persistence.*;

@Entity
@Table(name="DeliveryAddress")
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliveryId")
    private Long deliveryId;

    @Column(name = "name")
    private String name;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "department")
    private String department;

    @ManyToOne
    @JoinColumn( name = "userId")
    private User user;

}
