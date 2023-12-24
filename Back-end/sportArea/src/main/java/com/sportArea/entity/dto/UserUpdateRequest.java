package com.sportArea.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

        private Long userId;

        private String firstName;

        private String lastName;

        private String email;

        private String phoneNumber;

//        private String password;
//
//        private Role role;
//
//        private Status status;
//
//        private TypeRegistration typeRegistration;


}
