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
public class UserUpdateLastName {

    @NotEmpty(message = "Last Name can't be empty.")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct Last Name. Use only chars. Min 3 not more than 30.")
    private String lastName;
}
