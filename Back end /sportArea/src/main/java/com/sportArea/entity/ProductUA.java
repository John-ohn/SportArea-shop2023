package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_ua")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brands")
    private String brands;

    @Column(name = "type")
    private String type;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "form_of_issue")
    private String formOfIssue;

    @Column(name = "producing_country")
    private String producingCountry;

    @Column(name = "taste")
    private String taste;

    @Column(name = "price")
    private long price;

    @Column(name = "article")
    private long article;

    @Column(name = "product_amount")
    private int productAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "product_consist")
    private String productConsist;

    @Column(name = "rating")
    private int rating;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToOne(mappedBy = "productUA")
    private ProductUS productUS;

}
