package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends AbstractGeneralException {

    public ProductException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
