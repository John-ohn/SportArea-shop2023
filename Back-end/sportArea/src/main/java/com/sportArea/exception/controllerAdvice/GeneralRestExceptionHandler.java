package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.GeneralException;
import com.sportArea.exception.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;


@ControllerAdvice
public class GeneralRestExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GeneralRestExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(GeneralException exc) {
        ErrorResponse userErrorResponse = new ErrorResponse(
                exc.getHttpStatus().value(),
                exc.getMessage(),
                new Timestamp(System.currentTimeMillis()));
        logger.warn("From GeneralRestExceptionHandler method -handlerException- send message error ({})",
                userErrorResponse.getMessage());

        return new ResponseEntity<>(userErrorResponse, exc.getHttpStatus());
    }

}
