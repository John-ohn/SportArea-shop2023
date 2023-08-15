package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductExeption extends RuntimeException {

    private HttpStatus httpStatus;

    public ProductExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductExeption(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ProductExeption(String message) {
        super(message);
    }

    public ProductExeption(Throwable cause) {
        super(cause);
    }
}
