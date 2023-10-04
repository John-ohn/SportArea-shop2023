package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends AbstractGeneralException {

    public OrderException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
