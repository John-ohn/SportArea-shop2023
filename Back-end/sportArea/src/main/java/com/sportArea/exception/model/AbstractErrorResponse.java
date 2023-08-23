package com.sportArea.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractErrorResponse {
    private int status;
    private String message;
    private Timestamp timeStamp;
}
