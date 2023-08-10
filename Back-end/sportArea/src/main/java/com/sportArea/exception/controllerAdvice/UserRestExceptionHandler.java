package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.UserException;
import com.sportArea.exception.model.UserErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class UserRestExceptionHandler {

    Logger logger = LoggerFactory.getLogger(UserRestExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handlerException(UserException exc) {
        UserErrorResponse userErrorResponse = new UserErrorResponse();
        userErrorResponse.setStatus(exc.getHttpStatus().value());
        userErrorResponse.setMessage(exc.getMessage());
        userErrorResponse.setTimeStamp(System.currentTimeMillis());
        logger.warn("From UserRestExceptionHandler method -handlerException- send message error ({})",
                userErrorResponse.getMessage());

        return new ResponseEntity<>(userErrorResponse, exc.getHttpStatus());
    }

}
