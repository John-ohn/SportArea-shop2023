package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.CommentException;
import com.sportArea.exception.model.CommentErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class CommentExceptionHandler {

    Logger logger = LoggerFactory.getLogger(UserRestExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<CommentErrorResponse> handlerException(CommentException exc) {
        CommentErrorResponse commentErrorResponse = new CommentErrorResponse(
                exc.getHttpStatus().value(),
                exc.getMessage(),
                new Timestamp(System.currentTimeMillis()));

        logger.warn("From CommentExceptionHandler method -handlerException- send message error ({})",
                commentErrorResponse.getMessage());

        return new ResponseEntity<>(commentErrorResponse, exc.getHttpStatus());
    }
}
