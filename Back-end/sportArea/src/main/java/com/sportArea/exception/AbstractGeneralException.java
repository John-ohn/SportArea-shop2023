package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractGeneralException extends RuntimeException {

    private HttpStatus httpStatus;

    public AbstractGeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractGeneralException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AbstractGeneralException(String message) {
        super(message);
    }

    public AbstractGeneralException(Throwable cause) {
        super(cause);
    }
}

