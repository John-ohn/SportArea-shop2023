package com.sportArea.entity.dto.restJWT;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class AuthenticationGoogleRequestDto {

//    @NotEmpty
    private String token;

}
