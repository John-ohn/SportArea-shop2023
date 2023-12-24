package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "MailSubscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptionId")
    private Long subscriptionId;

    @Column(name = "email")
    @NotEmpty(message = "Email can't be empty.")
    @Email(message = "Please enter a valid email", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}")
    private String email;

}
