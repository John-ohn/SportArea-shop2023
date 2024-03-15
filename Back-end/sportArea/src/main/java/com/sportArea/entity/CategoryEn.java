package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "CategoryEn")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "categoryName")
    private String categoryName;

    @OneToMany(mappedBy = "categoryEn", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<SubCategoryEn> subCategoriesEn;
}
