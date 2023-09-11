package com.sportArea.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class OrderErrorResponse{

    private int status;
    private String message;
    private Timestamp timeStamp;

    public OrderErrorResponse(int status, String message, Timestamp timeStamp){
        this.status=status;
        this.message=message;
        this.timeStamp=timeStamp;
    }
}
