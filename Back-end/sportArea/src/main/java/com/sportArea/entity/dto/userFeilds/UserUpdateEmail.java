package com.sportArea.entity.dto.userFeilds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateEmail {

    @NotEmpty(message = "Email can't be empty.")
    @Email(message = "Please enter a valid email", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}")
    private String email;
}
