package com.sportArea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SubCategoryUa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryUa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subCategoryId")
    private Long subCategoryId;

    @Column(name = "subCategoryName")
    private String subCategoryName;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonIgnore
    private CategoryUa category;
}
