package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ProductUA")
@Data
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
    private Long price;

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

    @ManyToOne
    @JoinColumn(name = "languageId")
    private Language language;

    @OneToOne(mappedBy = "productUA")
    private ProductUS productUS;

}
