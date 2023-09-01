package com.sportArea.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
public class UserErrorResponse extends AbstractErrorResponse{

    public UserErrorResponse(int status, String message, Timestamp timeStamp){
        super(status, message, timeStamp);
    }



}
