package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.util.Objects;

@ControllerAdvice
public class ValidExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ValidExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(MethodArgumentNotValidException exc) {
        ErrorResponse userErrorResponse = new ErrorResponse();
        userErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        userErrorResponse.setMessage(Objects.requireNonNull(exc.getFieldError()).getDefaultMessage());
        userErrorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        logger.warn("From ValidExceptionHandler method -handlerException- send message error ({})",
                userErrorResponse.getMessage());

        return new ResponseEntity<>(userErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
