package com.sportArea.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {


    private Long userId;

    @NotEmpty(message = "is required")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct First Name. Use only chars. Not more than 30.")
    private String firstName;

    @NotEmpty(message = "is required")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct Last Name. Use only chars. Not more than 30.")
    private String lastName;

    @NotEmpty(message = "is required")
    @Email(message = "Please enter a valid email", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}")
    private String email;

    @NotEmpty(message = "is required")
    @Pattern(regexp = "^[0-9]{10,12}", message = "Write a correct phone number. Use only numbers. Not more than 12.")
    private String phoneNumber;

    @NotBlank(message = "Password must not be blank")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.* ).{8,70}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, no space")
    private String password;


    private Role role;


    private Status status;
}
