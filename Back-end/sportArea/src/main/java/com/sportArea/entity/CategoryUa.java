package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "CategoryUa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "categoryName")
    private String categoryName;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<SubCategoryUa> subCategories;

    @OneToOne
    @JoinColumn(name = "categoryenid")
    private CategoryEn categoryEn;
}
