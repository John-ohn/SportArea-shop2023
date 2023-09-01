package com.sportArea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommentException extends AbstractGeneralException{
    public CommentException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
