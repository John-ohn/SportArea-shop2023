package com.sportArea.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @NotEmpty(message = "is required")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct First Name. Use only chars. Not more than 30.")
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty(message = "is required")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct Last Name. Use only chars. Not more than 30.")
    @Column(name = "lastName")
    private String lastName;

    @NotEmpty(message = "is required")
    @Email(message = "Please enter a valid email", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "is required")
    @Pattern(regexp = "^[0-9]{10,12}", message = "Write a correct phone number. Use only numbers. Not more than 12.")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @JsonIgnore
    @NotBlank(message = "Password must not be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.* ).{8,70}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, no space")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
