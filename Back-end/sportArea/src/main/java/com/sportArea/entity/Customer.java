package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Guest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @Column(name = "guestId" )
    private Long guestId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
