package com.sportArea.entity.dto;

import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTOUpdate {

    @NotNull(message = "User Id can't be empty or null.")
    private Long userId;

    @NotEmpty(message = "First Name can't be empty.")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct First Name. Use only chars. Min 3 not more than 30.")
    private String firstName;

    @NotEmpty(message = "Last Name can't be empty.")
    @Pattern(regexp = "^[\\p{L}]{3,30}", message = "Write a correct Last Name. Use only chars. Min 3 not more than 30.")
    private String lastName;

    @NotEmpty(message = "Email can't be empty.")
    @Email(message = "Please enter a valid email", regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z]+\\.[a-zA-Z.]{2,5}")
    private String email;

    @NotEmpty(message = "Phone number can't be empty.")
    @Pattern(regexp = "^[0-9]{10,12}", message = "Write a correct phone number. Use only numbers. Min 10 not more than 12.")
    private String phoneNumber;

//    @NotBlank(message = "Password must not be blank")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.* ).{8,70}$",
//            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, no space")
    private String password;

    private Role role;


    private Status status;

    private TypeRegistration typeRegistration;
}
