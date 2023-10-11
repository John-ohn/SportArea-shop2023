package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogException extends AbstractGeneralException {

    public BlogException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
