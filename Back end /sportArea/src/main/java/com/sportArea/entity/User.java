package com.sportArea.entity;

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

    @NotEmpty(message ="is required")
    @Pattern(regexp="^[a-zA-Z]{3,30}",message="Write a correct First Name. Use only chars. Not more than 30.")
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty(message ="is required")
    @Pattern(regexp="^[a-zA-Z]{3,30}",message="Write a correct First Name. Use only chars. Not more than 30.")
    @Column(name = "lastName")
    private String lastName;

    @NotEmpty(message ="is required")
    @Pattern(regexp="^[a-zA-Z0-9._-]{3,30}",message="Write a correct User Name. You can use chars, numbers, symbols . _ - . Not more than 30.")
    @Column(name = "userName")
    private String userName;

    @NotEmpty(message ="is required")
    @Email(message = "Please enter a valid email", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    @Column(name = "email")
    private String email;

//    @Pattern(regexp="^[0-9]{10,13}",message="Write a correct First Name. Use only numbers. Not more than 13.")
    @Column(name = "phoneNumber")
    private Long phoneNumber;

    @NotBlank(message = "Password must not be blank")
    @Size(min=6, max=30, message = "Password must be between 8 and 30 characters")
//    @NotEmpty(message ="is required")
//    @Pattern(regexp="^[a-zA-Z0-9]{8,30}",message="Write a correct password. Use only chars and numbers. From 8 to 30.")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

}
