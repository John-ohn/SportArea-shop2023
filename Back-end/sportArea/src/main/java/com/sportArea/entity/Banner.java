package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Banner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {

    @Id
    @Column(name="bannerId")
    private Long bannerId;

    @Column(name="bannerName")
    private String bannerName;

    @Column(name = "bannerUrl")
    private String bannerUrl;
}
