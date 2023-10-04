package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TargetCategory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "nameCategory")
    private String  nameCategory;

    @Column(name = "translateName")
    private String  translateName;

    @Column(name = "iconHttp")
    private String iconHttp;

}
