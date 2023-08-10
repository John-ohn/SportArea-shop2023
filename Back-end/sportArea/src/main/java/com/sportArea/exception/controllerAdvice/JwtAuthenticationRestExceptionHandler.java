package com.sportArea.exception.controllerAdvice;

import com.sportArea.exception.JwtAuthenticationException;
import com.sportArea.exception.model.JwtAuthenticationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtAuthenticationRestExceptionHandler {
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationRestExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<JwtAuthenticationErrorResponse> handlerException(JwtAuthenticationException exc) {
        JwtAuthenticationErrorResponse jwtAuthenticationError = new JwtAuthenticationErrorResponse();
        jwtAuthenticationError.setStatus(exc.getHttpStatus().value());
        jwtAuthenticationError.setMessage(exc.getMessage());
        jwtAuthenticationError.setTimeStamp(System.currentTimeMillis());
        logger.warn("From UserRestExceptionHandler method -handlerException- send message error ({})",
                jwtAuthenticationError.getMessage());

        return new ResponseEntity<>(jwtAuthenticationError, exc.getHttpStatus());
    }
}
