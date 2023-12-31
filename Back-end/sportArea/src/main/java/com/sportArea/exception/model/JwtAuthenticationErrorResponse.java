package com.sportArea.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
