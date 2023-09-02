package com.sportArea.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProductUA")
@ToString(exclude ="response" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUA {

    @Id
    @Column(name = "productId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "brands")
    private String brands;

    @Column(name = "type")
    private String type;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "formOfIssue")
    private String formOfIssue;

    @Column(name = "producingCountry")
    private String producingCountry;

    @Column(name = "taste")
    private String taste;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name="promotionPrice")
    private BigDecimal promotionPrice;

    @Column(name="currency")
    private String currency;

    @Column(name="weight")
    private String weight;

    @Column(name = "article")
    private Long article;

    @Column(name = "productAmount")
    private Integer productAmount;

    @Column(name = "description")
    private String description;

    @Column(name = "productConsist")
    private String productConsist;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "status")
    private String status;

    @Column(name = "promotion", columnDefinition = "INT default 0")
    private Integer promotion;

    @Column(name="urlImage")
    private String urlImage;
//
//    @OneToMany(mappedBy = "productId")
//    private List<Response> response;

}
