package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.BlogException;
import com.sportArea.exception.model.BlogErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class BlogExceptionHandler {

    Logger logger = LoggerFactory.getLogger(BlogExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<BlogErrorResponse> handlerException(BlogException exc) {
        BlogErrorResponse blogErrorResponse = new BlogErrorResponse( exc.getHttpStatus().value(),
                exc.getMessage(),
                new Timestamp(System.currentTimeMillis()));

        logger.warn("From BlogExceptionHandler method -handlerException- send message error ({})",
                blogErrorResponse.getMessage());

        return new ResponseEntity<>(blogErrorResponse, exc.getHttpStatus());
    }

}
