package com.sportArea.entity.dto.userFeilds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePhoneNumber {

    @NotEmpty(message = "Phone number can't be empty.")
    @Pattern(regexp = "^[0-9]{10,12}", message = "Write a correct phone number. Use only numbers. Min 10 not more than 12.")
    private String phoneNumber;
}
