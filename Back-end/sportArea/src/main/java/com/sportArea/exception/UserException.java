package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends AbstractGeneralException {

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
