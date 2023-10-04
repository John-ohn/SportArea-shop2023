package com.sportArea.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class OrderErrorResponse extends AbstractErrorResponse {

    public OrderErrorResponse(int status, String message, Timestamp timeStamp) {
        super(status, message, timeStamp);
    }
}
