package com.sportArea.entity.dto.userFeilds;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateFirstName {

    @NotEmpty(message = "First Name can't be empty.")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct First Name. Use only chars. Min 3 not more than 30.")
    private String firstName;

}
